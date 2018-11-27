package com.dangdang.logtest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class SlaService {

    final Logger LOG = LoggerFactory.getLogger(SlaService.class);

    private static final SlaService instance = new SlaService();

    final String API_SLA_LOG = "api_sla_log";
    final String API_SLA_LOG_APP = "api_sla_log_app";

    private DataSource ds = null;

    private SlaService() {
        Context initContext;
        try {
            initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            this.ds = (DataSource) envContext.lookup("jdbc/TestDB");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static SlaService getInstance() {
        return instance;
    }

    public Connection getConn() {
        Connection conn = null;
        try {
            conn = this.ds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public void saveSla(List<Map<String, String>> list, String ip, String type, String timestamp) {
        if (list != null) {
            String table = API_SLA_LOG;
            if (type.equalsIgnoreCase("app")) {
                table = API_SLA_LOG_APP;
            }
            String sql = " INSERT INTO " + table + " ( ip, api_url, slatime,total,success,fail,sla500,sla800,sla995,sla999,apiId,sysId,stattype ) "
                    + " values( ?,?,?,?,?,?,?,?,?,?,?,?,? )";
            try (Connection conn = this.getConn(); PreparedStatement ps = conn.prepareStatement(sql);) {
                try {
                    conn.setAutoCommit(false);
                    for (Map<String, String> map : list) {
                        int index = 1;
                        ps.setObject(index++, ip);
                        ps.setObject(index++, map.get("apiurl"));
                        ps.setObject(index++, timestamp);
                        ps.setObject(index++, map.get("total"));
                        ps.setObject(index++, map.get("success") == null ? 0 : map.get("success"));
                        ps.setObject(index++, map.get("fail") == null ? 0 : map.get("fail"));
                        ps.setObject(index++, map.get("sla500") == null || map.get("sla500").trim().length() == 0 ? 0 : map.get("sla500"));
                        ps.setObject(index++, map.get("sla800") == null || map.get("sla800").trim().length() == 0 ? 0 : map.get("sla800"));
                        ps.setObject(index++, map.get("sla995") == null || map.get("sla995").trim().length() == 0 ? 0 : map.get("sla995"));
                        ps.setObject(index++, map.get("sla999") == null || map.get("sla999").trim().length() == 0 ? 0 : map.get("sla999"));
                        ps.setObject(index++, map.get("apiId") == null ? 0 : map.get("apiId"));
                        ps.setObject(index++, map.get("sysId") == null ? 0 : map.get("sysId"));
                        ps.setObject(index++, map.get("stattype") == null ? 0 : map.get("stattype"));
                        ps.addBatch();
                    }
                    ps.executeBatch();
                    conn.commit();
                } catch (Exception e) {
                    LOG.error("insert into " + table + " error : ", e);
                    conn.rollback();
                }
            } catch (Exception e) {
                LOG.error("insert into " + table + " error : ", e);
            }
        }
    }

    public List<SlaBean> getSla(String apiId, String sysId, String start, String end, String type, String ip, String stattype) {
        List<SlaBean> list = new ArrayList<>();
        String sql = this.getSql(type, ip, start);
        try (Connection conn = this.getConn(); PreparedStatement ps = conn.prepareStatement(sql)) {
            int index = 1;
            ps.setString(index++, apiId);
            ps.setString(index++, sysId);
            ps.setString(index++, start);
            ps.setString(index++, end);
            ps.setString(index++, stattype);
            if (!ip.equals("0")) {
                ps.setString(index++, ip);
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SlaBean sla = new SlaBean();
                sla.setSlatime(rs.getString(1));
                sla.setSla500(rs.getInt(2));
                sla.setSla800(rs.getInt(3));
                sla.setSla995(rs.getInt(4));
                sla.setSla999(rs.getInt(5));
                sla.setTotal(rs.getInt(6));
                list.add(sla);
            }
        } catch (Exception e) {
            LOG.error("select sla table error : ", e);
        }
        return list;
    }

    private String getSql(String type, String ip, String start) {
        String table = API_SLA_LOG;
        if (type.equalsIgnoreCase("app")) {
            table = API_SLA_LOG_APP;
        }
        String curDate = getDate(null);
        String reqDate = start.split(" ")[0].replace("-", "");
        if (!curDate.equals(reqDate)) {
            table = table + "_" + reqDate;
        }
        String sql = "";
        if (ip.equals("0")) {
            sql = "SELECT slatime ,"
                    + " floor(avg(t.sla500)), "
                    + " floor(avg(t.sla800)), "
                    + " floor(avg(t.sla995)), "
                    + " floor(avg(t.sla999)), "
                    + " sum(t.total) from ( "
                    + " SELECT slatime , apiId , ip, "
                    + " max(sla500) sla500,"
                    + " max(sla800) sla800, "
                    + " max(sla995) sla995, "
                    + " max(sla999) sla999, "
                    + " sum(total) total"
                    + " FROM " + table + " where apiId = ? and sysId = ? and slatime >= ? and slatime <= ? and stattype = ?"
                    + " group by slatime,ip,apiId ) t group by t.slatime";
        } else {
            sql = "SELECT slatime , "
                    + "max(sla500), "
                    + "max(sla800), "
                    + "max(sla995), "
                    + "max(sla999), "
                    + "sum(total) "
                    + "FROM " + table + " where apiId = ? and sysId = ? and slatime >= ? and slatime <= ? and stattype = ? and ip = ? group by slatime";
        }
        return sql;
    }

    public Map<String, List<ApiBean>> getAPIs() {
        Map<String, List<ApiBean>> map = new HashMap<>();
        String sql = "SELECT api_url,api_name,apiId,sysId,category,logs, stattype FROM api_list ";
        try (Connection conn = this.getConn(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ApiBean bean = new ApiBean();
                bean.setApiUrl(rs.getString(1));
                bean.setApiName(rs.getString(2));
                bean.setApiId(rs.getString(3));
                bean.setSysId(rs.getString(4));
                bean.setCategory(rs.getString(5));
                bean.setLogs(rs.getString(6));
                bean.setStattype(rs.getString(7));
                if (map.get(bean.getCategory()) == null) {
                    map.put(bean.getCategory(), new ArrayList<ApiBean>());
                }
                map.get(bean.getCategory()).add(bean);
            }
        } catch (Exception e) {
            LOG.error("select sla table error : ", e);
        }
        return map;
    }

    public void saveApi(ApiBean bean) throws Exception {
        String sql = "SELECT api_url,api_name,apiId,sysId,category,logs FROM api_list where apiId = ? and sysId = ?";
        try (Connection conn = this.getConn(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, bean.getApiId());
            ps.setString(2, bean.getSysId());
            ResultSet rs = ps.executeQuery();
            boolean update = false;
            while (rs.next()) {
                update = true;
                break;
            }
            PreparedStatement ps1 = null;
            if (update) {
                sql = "UPDATE api_list set api_url = ? , api_name = ? , category = ? , logs = ? where apiId = ? and sysId = ?";
                ps1 = conn.prepareStatement(sql);
                int index = 1;
                ps1.setString(index++, bean.getApiUrl());
                ps1.setString(index++, bean.getApiName());
                ps1.setString(index++, bean.getCategory());
                ps1.setString(index++, bean.getLogs());
                ps1.setString(index++, bean.getApiId());
                ps1.setString(index++, bean.getSysId());
                ps1.execute();
            } else {
                sql = "INSERT INTO api_list(api_url,api_name,apiId,sysId,category,logs) values(?,?,?,?,?,?)";
                ps1 = conn.prepareStatement(sql);
                int index = 1;
                ps1.setString(index++, bean.getApiUrl());
                ps1.setString(index++, bean.getApiName());
                ps1.setString(index++, bean.getApiId());
                ps1.setString(index++, bean.getSysId());
                ps1.setString(index++, bean.getCategory());
                ps1.setString(index++, bean.getLogs());
                ps1.execute();
            }
        } catch (Exception e) {
            LOG.error("save api error : ", e);
            throw e;
        }
    }

    public List<String> getIpList(String sysId, String apiId, String logs) {
        List<String> list = new ArrayList<>();
        String table = API_SLA_LOG;
        if (logs.equalsIgnoreCase("2")) {
            table = API_SLA_LOG_APP;
        }
        String sql = "SELECT distinct ip FROM " + table + " where apiId = ? and sysId = ? and slatime > ? order by ip ";
        try (Connection conn = this.getConn(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, apiId);
            ps.setString(2, sysId);
            ps.setString(3, getTime(-5));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(rs.getString(1));
            }
        } catch (Exception e) {
            LOG.error("select sla table error : ", e);
        }
        return list;
    }

    public void deleteSlaData(String type, String timestamp) {
        String table = API_SLA_LOG;
        if (type.equalsIgnoreCase("app")) {
            table = API_SLA_LOG_APP;
        }
        String sql = " delete from " + table + " where slatime <= ? ";
        try (Connection conn = this.getConn(); PreparedStatement ps = conn.prepareStatement(sql);) {
            try {
                ps.setString(1, timestamp);
                ps.execute();
            } catch (Exception e) {
                LOG.error("delete from " + table + " error : ", e);
                conn.rollback();
            }
        } catch (Exception e) {
            LOG.error("delete from " + table + " error : ", e);
        }
    }

    public void backupSlaData(String type, Date start, Date end) {
        String table = API_SLA_LOG;
        if (type.equalsIgnoreCase("app")) {
            table = API_SLA_LOG_APP;
        }
        String bckTable = getBackupTableName(table, start);
        String sql = "insert into " + bckTable
                + "(ip, api_url, slatime,total,success,fail,sla500,sla800,sla995,sla999,apiId,sysId,stattype) "
                + "(select ip, api_url, slatime,total,success,fail,sla500,sla800,sla995,sla999,apiId,sysId,stattype from "
                + table + " where slatime >= ? and slatime < ?)";
        try (Connection conn = this.getConn(); PreparedStatement ps = conn.prepareStatement(sql);) {
            try {
                ps.setString(1, getTime(start));
                ps.setString(2, getTime(end));
                ps.execute();
            } catch (Exception e) {
                LOG.error("backupSlaData to " + bckTable + " error : ", e);
                conn.rollback();
            }
        } catch (Exception e) {
            LOG.error("backupSlaData to " + bckTable + " error : ", e);
        }
    }

    public JsonBean getSlaData(String apiId, String sysId, String start, String end, String type, String ip, String stattype) {
        List<SlaBean> list = this.getSla(apiId, sysId, start, end, type, ip, stattype);

        JsonBean jsonBean = new JsonBean();
        SeriesBean sla500 = new SeriesBean("SLA500", 0);
        SeriesBean sla800 = new SeriesBean("SLA800", 0);
        SeriesBean sla995 = new SeriesBean("SLA995", 0);
        SeriesBean sla999 = new SeriesBean("SLA999", 0);
        SeriesBean total = new SeriesBean("TOTAL", "spline", 1);

        jsonBean.getSlaSeries().add(sla500);
        jsonBean.getSlaSeries().add(sla800);
        jsonBean.getSlaSeries().add(sla995);
        jsonBean.getSlaSeries().add(sla999);
        jsonBean.getSlaSeries().add(total);

        for (SlaBean bean : list) {
            String saltime = bean.getSlatime();
            jsonBean.getCategories().add(saltime);
            sla500.getData().add(bean.getSla500());
            sla800.getData().add(bean.getSla800());
            sla995.getData().add(bean.getSla995());
            sla999.getData().add(bean.getSla999());
            total.getData().add(bean.getTotal());
        }
        return jsonBean;
    }

    public String getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Calendar c = Calendar.getInstance();
        return sdf.format(c.getTime());
    }

    public String getTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return sdf.format(c.getTime());
    }

    public String getTime(int i) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MINUTE, i);//1分钟前
        return sdf.format(c.getTime());
    }

    public String getDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar c = Calendar.getInstance();
        if (date != null) {
            c.setTime(date);
        }
        return sdf.format(c.getTime());
    }

    public String createTable(Date date) {
        String t1 = getBackupTableName(API_SLA_LOG, date);
        String t2 = getBackupTableName(API_SLA_LOG_APP, date);
        String sql1 = "CREATE TABLE `" + t1 + "` ( `id` bigint(20) NOT NULL AUTO_INCREMENT, `ip` varchar(255) DEFAULT NULL, `api_url` varchar(512) DEFAULT NULL, `apiId` int(11) DEFAULT NULL, `sysId` int(11) DEFAULT NULL, `slatime` varchar(45) DEFAULT NULL, `total` int(11) DEFAULT NULL, `success` int(11) DEFAULT NULL, `fail` int(11) DEFAULT NULL, `sla500` int(11) DEFAULT NULL, `sla800` int(11) DEFAULT NULL, `sla995` int(11) DEFAULT NULL, `sla999` int(11) DEFAULT NULL, `stattype` varchar(45) DEFAULT NULL, PRIMARY KEY (`id`), KEY `index_ip` (`ip`) USING BTREE, KEY `index_apiid` (`apiId`), KEY `index_sysid` (`sysId`), KEY `index_slatime` (`slatime`), KEY `index_stattype` (`stattype`) ) ENGINE=InnoDB AUTO_INCREMENT=88757740 DEFAULT CHARSET=utf8;";
        String sql2 = "CREATE TABLE `" + t2 + "` ( `id` bigint(20) NOT NULL AUTO_INCREMENT, `ip` varchar(255) DEFAULT NULL, `api_url` varchar(512) DEFAULT NULL, `apiId` int(11) DEFAULT NULL, `sysId` int(11) DEFAULT NULL, `slatime` varchar(45) DEFAULT NULL, `total` int(11) DEFAULT NULL, `success` int(11) DEFAULT NULL, `fail` int(11) DEFAULT NULL, `sla500` int(11) DEFAULT NULL, `sla800` int(11) DEFAULT NULL, `sla995` int(11) DEFAULT NULL, `sla999` int(11) DEFAULT NULL, `stattype` varchar(45) DEFAULT NULL, PRIMARY KEY (`id`), KEY `index_ip` (`ip`) USING BTREE, KEY `index_apiid` (`apiId`), KEY `index_sysid` (`sysId`), KEY `index_slatime` (`slatime`), KEY `index_stattype` (`stattype`) ) ENGINE=InnoDB AUTO_INCREMENT=88757740 DEFAULT CHARSET=utf8;";
        try (Connection conn = this.getConn(); Statement stmt = conn.createStatement();) {
            stmt.executeUpdate(sql1);
            stmt.executeUpdate(sql2);
        } catch (SQLException e) {
            LOG.error("create table {}，{} error : ", t1, t2, e);
        }
        return t1 + "," + t2;
    }

    private String getBackupTableName(String table, Date start) {
        String curDate = getDate(start);
        table = table + "_" + curDate;
        return table;
    }
}
