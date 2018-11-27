<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.dangdang.logtest.*" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>采销与购买平台API监控</title>
    <%@ include file="/common/taglibs.jsp" %>
    <style type="text/css">
        .main_contain {
            margin-top: 20px;
            margin-left: auto;
            margin-right: auto;
            height: 80%;
            width: 95%;
            min-width: 900px;
        }
    </style>
    <script type="text/javascript" src="js/hostInfo.js"></script>
    <script type="text/javascript" src="js/main.js"></script>
</head>
<body>
<%
    SlaService service = SlaService.getInstance();
    Map<String, List<ApiBean>> apis = service.getAPIs();
    request.setAttribute("apis", apis);
    
    class A{
    	
    }
%>
<jsp:include page="/common/nav.jsp"></jsp:include>

<div id="main_contain" class="main_contain">
    <a id="clear-link" href="javascript:void(0)">清空</a>
    开始时间：<input type="text" id="datepicker_start" class="date" size="30" value="">
    结束时间：<input type="text" id="datepicker_end" class="date" size="30" value="">
    <button type="button" class="btn btn-info btn-sm" id="see">查看</button>
    &nbsp;&nbsp;<input id="auto-refresh" type="checkbox" checked="checked"/> &nbsp;自动刷新
</div>
<div id="container1_title" class="main_contain" style="display:none;"><span class="label label-success">容器SLA</span>
</div>
<div id="container1" class="main_contain"></div>
<div id="container2_title" class="main_contain" style="display:none;"><span class="label label-success">程序SLA</span>
</div>
<div id="container2" class="main_contain"></div>
</body>
</html>