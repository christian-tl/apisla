package com.dangdang.logtest;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.TriggerUtils;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BackupJob implements Job {

    Logger LOG = LoggerFactory.getLogger(BackupJob.class);
    private static int INTERNAL = 5;
    private static final String BACKUP_CRON = "0 0/" + INTERNAL + " * * * ?";

    public void execute(JobExecutionContext context) throws JobExecutionException {
        SlaService service = SlaService.getInstance();
        Date curDate = new Date();
        Date end = new Date();
        CronTriggerImpl cronTriggerImpl = new CronTriggerImpl();
        try {
            cronTriggerImpl.setCronExpression(BACKUP_CRON);
            List<Date> fireTimes = TriggerUtils.computeFireTimes(cronTriggerImpl, null, 1);
            if (fireTimes.size() > 0) {
                end = fireTimes.get(0);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        end = addDateMin(end, -INTERNAL);
        Date start = addDateMin(end, -INTERNAL);
        if (service.getTime(curDate).contains("00:00")) {
            String tables = service.createTable(curDate);
            LOG.info("BackupJob created new tables : " + tables);
        }
        service.backupSlaData("container", start, end);
        service.backupSlaData("app", start, end);
        LOG.info("BackupJob backup sla data, {} - {}", service.getTime(start), service.getTime(end));
    }

    public static String getBackupCron() {
        return BACKUP_CRON;
    }

    private Date addDateMin(Date date, int x) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, x);
        return cal.getTime();
    }
}
