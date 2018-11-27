$(function () {
    var interval1, interval2;
    var interval = 1000 * 60;

    var hash = window.location.hash;
    if (hash.length > 0) {
        renderChart(hash);
    } else {
        var firstLink = $("#first-menu").find("li").eq(0).find("a");
        renderChart(firstLink.attr("href"));
    }

    $(".api-menu-link").click(function () {
        renderChart($(this).attr("href"));
    });

    $("#clear-link").click(function () {
        $("#main_contain").find(".date").val("");
    });

    function renderChart(hash) {
        var params = hash.replace("#", "").split(",");
        var reqInfo = {};
        reqInfo.ip = $("#ip").val();
        reqInfo.apiId = params[0];
        reqInfo.sysId = params[1];
        reqInfo.logs = params[2];
        reqInfo.statType = params[3];
        reqInfo.apiUrl = params[4];
        reqInfo.apiName = params[5];
        var mip = params[6];
        var custom = false;
        if ( typeof mip != "undefined" && mip != null) {
            reqInfo.ip = mip;
            custom = true;
        }

        initDatepicker();

        initPanel(reqInfo.logs);

        initIpList(reqInfo,custom, function (reqInfo) {

            bindEvents(reqInfo);

            autoRefreshSetting(reqInfo);

            $('#see').click();
        });
    }

    function initDatepicker() {
        var myDate = new Date();
        var hours = myDate.getHours();
        var minutes = myDate.getMinutes();
        $.datepicker.setDefaults({
            dateFormat: "yy-mm-dd"
        });

        $("#datepicker_start").datetimepicker({
            timeFormat: 'HH:mm',
            hour: hours,
            minute: minutes
        });
        $("#datepicker_end").datetimepicker({
            timeFormat: 'HH:mm',
            hour: hours,
            minute: minutes
        });
    }

    function initPanel(logsType) {
        if (logsType == '3') {
            $("#container1_title,#container2_title,#container1,#container2").show();
        } else if (logsType == '1') {
            $("#container1_title,#container1").show();
            $("#container2_title,#container2").hide();
        } else if (logsType == '2') {
            $("#container2_title,#container2").show();
            $("#container1_title,#container1").hide();
        }
    }

    function initIpList(reqInfo, custom, oncomp) {
        $.ajax({
            url: "iplist?apiId=" + reqInfo.apiId + "&sysId=" + reqInfo.sysId + "&logs=" + reqInfo.logs,
            dataType: "json",
            success: function (data) {
                var list = data.ipList;
                var hasCurIP = false;
                var dropList = $("#ip");
                dropList.find("option:not(.reserved)").remove();
                var count = list.length;
                for (var i = 0; i < count; i++) {
                    var ip_ = list[i];
                    var color = getShowColor(ip_);
                    dropList.append("<option style='color:" + color +"' value='" + ip_ + "'>" + ip_ + "</option>");
                    if (reqInfo.ip === ip_) {
                        hasCurIP = true;
                    }
                }
                var selIp = $("#ip");
                selIp.find("option").eq(0).text("集群 ["+ count +"]");
                if (!hasCurIP) {
                    if (custom) {
                        selIp.append("<option value='"+ reqInfo.ip +"'>" + reqInfo.ip + "</option>");
                    }else {
                        reqInfo.ip = "0";
                    }
                }
                dropList.val(reqInfo.ip);
                oncomp(reqInfo);
            }
        });
    }

    function autoRefreshSetting(reqInfo) {
        var logs = reqInfo.logs;
        clearInterval(interval1);
        clearInterval(interval2);
        var $refresh = $("#auto-refresh");
        var checked = $refresh.prop("checked");
        if (logs == '3') {
            if (checked) {
                interval1 = setInterval(function () {
                    disSLA(reqInfo, "container", "container1");
                }, interval);
                interval2 = setInterval(function () {
                    disSLA(reqInfo, "app", "container2");
                }, interval);
            }
        } else if (logs == '1') {
            if (checked) {
                interval1 = setInterval(function () {
                    disSLA(reqInfo, "container", "container1");
                }, interval);
            }
        } else if (logs == '2') {
            if (checked) {
                interval2 = setInterval(function () {
                    disSLA(reqInfo, "app", "container2");
                }, interval);
            }
        }
    }

    function ChartOption(apiName, apiUrl) {
        this.chart = {
            type: 'column',
            renderTo: 'container1'
        };
        this.title = {
            text: apiName
        };
        this.subtitle = {
            text: apiUrl
        };
        this.xAxis = {
            categories: [],
            crosshair: true
        };
        this.yAxis = [{
            min: 0,
            title: {
                text: 'API SLA (ms)'
            }
        }, {
            min: 0,
            title: {
                text: 'API总共调用次数'
            },
            opposite: true
        }
        ];
        this.tooltip = {
            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
            '<td style="padding:0">&nbsp;&nbsp;<b>{point.y:f}</b></td></tr>',
            footerFormat: '</table>',
            shared: true,
            useHTML: true
        };
        this.plotOptions = {
            column: {
                dataLabels: {
                    enabled: true
                },
                enableMouseTracking: true
            },
            spline: {
                dataLabels: {
                    enabled: true
                },
                enableMouseTracking: true
            }
        };
        this.series = []
    }

    function disSLA(reqInfo, type, divId) {
        var a = new ChartOption(reqInfo.apiName, reqInfo.apiUrl);
        var params = getParams();
        var start = params.start;
        var end = params.end;
        var ip = params.ip;
        $.ajax({
            url: "slaview?type=" + type + "&apiurl=" + reqInfo.apiUrl + "&apiId=" + reqInfo.apiId + "&sysId=" + reqInfo.sysId + "&ip=" + ip
            + "&stattype=" + reqInfo.statType + "&start=" + start + "&end=" + end,
            dataType: "json",
            success: function (data) {
                a.xAxis.categories = data.categories;
                a.series = data.slaSeries;
                a.chart.renderTo = divId;
                new Highcharts.Chart(a);
            }
        });
    }

    function bindEvents(reqInfo) {
        $('#ip').unbind("change").change(function () {
            $('#see').click();
        });

        $('#see').unbind("click").click(function () {
            var logs = reqInfo.logs;
            if (logs == '3') {
                disSLA(reqInfo, "container", "container1");
                disSLA(reqInfo, "app", "container2");
            } else if (logs == '1') {
                disSLA(reqInfo, "container", "container1");
            } else if (logs == '2') {
                disSLA(reqInfo, "app", "container2");
            }
        });

        $("#auto-refresh").unbind("change").change(function () {
            autoRefreshSetting(reqInfo);
        });
    }

    function getParams() {
        var query = {};
        query.start = $("#datepicker_start").val();
        query.end = $("#datepicker_end").val();
        query.ip = $("#ip").val();
        return query;
    }

});