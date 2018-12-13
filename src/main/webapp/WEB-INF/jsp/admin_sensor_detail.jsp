<%--
  Created by IntelliJ IDEA.
  User: Wongnoubo
  Date: 2018/11/19
  Time: 21:12
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${detail.name}</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <script src="js/jquery-3.2.1.js"></script>
    <script src="js/bootstrap.min.js" ></script>
    <script src="js/ajax.js"></script>
    <script src="js/echarts.min.js"></script>
    <style>
        body{
            background-color: rgb(240,242,245);
        }
    </style>

</head>
<body>
<nav  style="position:fixed;z-index: 999;width: 100%;background-color: #fff" class="navbar navbar-default" role="navigation" >
    <div class="container-fluid">
        <div class="navbar-header" style="margin-left: 8%;margin-right: 1%">
            <a class="navbar-brand" href="admin_main.html">家+安全系统</a>
        </div>
        <div class="collapse navbar-collapse" >
            <ul class="nav navbar-nav navbar-left">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        传感器管理
                        <b class="caret"></b>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="allsensors.html?adminId=${admin.adminId}">全部传感器</a></li>
                        <li class="divider"></li>
                        <li><a href="sensor_add.html">增加传感器</a></li>
                    </ul>
                </li>
                <li >
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" >
                        密码修改
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="admin_repasswd.html">密码修改</a></li>
                    </ul>
                </li>
                <li >
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" >
                        实时监控
                        <b class="caret"></b>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="http://192.168.0.26:8090/?action=stream">实时监控</a></li>
                    </ul>
                </li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="admininformation?adminId=${admin.adminId}"><span class="glyphicon glyphicon-user"></span>&nbsp;${admin.nickname}，已登录</a></li>
                <li><a href="logout.html"><span class="glyphicon glyphicon-log-in"></span>&nbsp;退出</a></li>
            </ul>
        </div>
    </div>
</nav>

<div class="col-xs-6 col-md-offset-3" style="position: relative;top: 10%">
    <div class="panel panel-primary">
        <div class="panel-heading">
            <h3 class="panel-title">${detail.name}</h3>
        </div>
        <div class="panel-body">
            <table class="table table-hover">
                <tr>
                    <th width="15%">传感器名</th>
                    <td>${detail.name}</td>
                </tr>
                <tr><c:choose>
                    <c:when test="${detail.name ne '红外人体传感器'}">
                        <th width="15%">示数</th>
                    </c:when>
                    <c:when test="${detail.name eq '红外人体传感器'}">
                        <th width="15%">是否有人经过</th>
                    </c:when>
                </c:choose>
                    <td><c:choose>
                        <c:when test="${detail.name eq '温度传感器'}">
                            <c:out value="${detail.temperature}℃"></c:out>
                            <script>setInterval("res('${detail.temperature}');",1000*20);</script>
                        </c:when>
                        <c:when test="${detail.name eq '湿度传感器'}">
                            <c:out value="${detail.humidity}%rh"></c:out>
                            <script>setInterval("res('${detail.humidity}');",1000*20);</script>
                        </c:when>
                        <c:when test="${detail.name eq '树莓派cpu温度'}">
                            <c:out value="${detail.cputemp}℃"></c:out>
                            <script>setInterval("res('${detail.cputemp}');",1000*20);</script>
                        </c:when>
                        <c:when test="${detail.name eq '红外人体传感器' && detail.humenState==1}">
                            <c:out value="有人经过"></c:out>
                            <script>setInterval("res('${detail.humenState}');",1000*20);</script>
                        </c:when>
                        <c:when test="${detail.name eq '红外人体传感器' && detail.humenState==0}">
                            <c:out value="无人经过"></c:out>
                            <script>setInterval("res('${detail.humenState}');",1000*20);</script>
                        </c:when>
                    </c:choose></td>
                </tr>
                <tr>
                    <th>数据库表</th>
                    <td>${detail.sensortableName}</td>
                </tr>
                <tr>
                    <th>位置</th>
                    <td>${detail.sensorAddress}</td>
                </tr>
                <tr>
                    <th>简介</th>
                    <td>${detail.sensorIntroduction}</td>
                </tr>
                <tr>
                    <th>价格</th>
                    <td>${detail.price}元</td>
                </tr>
                <tr>
                    <th>状态</th>
                    <c:if test="${detail.sensorState==1}">
                        <td>正常工作</td>
                    </c:if>
                    <c:if test="${detail.sensorState==0}">
                        <td>异常</td>
                    </c:if>
                </tr>
                </tbody>
            </table>
        </div>
        <div class="panel panel-info">
            <div class="panel-heading" id="mq2">
                <h4 class="panel-title">
                    <p data-toggle="collapse" href="#collapse3"
                       data-parent="#accordion" align="center">
                        ${detail.name}曲线图</p>
                </h4>
            </div>
        </div>
        <script>
            $(document).ready(function () {
                //放置图表的容器
                if(${detail.name eq '温度传感器'}){
                var group = $("#temperature");
                //设置容器的宽度、高度和背景颜色
                group.css({
                    "width": "100%",
                    "height": "45%",
                    "background-color": "aliceblue"
                });
                //创建图表对象
                var chart = echarts.init(group.get(0));
                chart.showLoading();//显示等待条
                //设置图表显示的内容
                var option = {
                    legend: {
                        selectedMode: false,//不可点击
                        data: ['温度']
                    },
                    grid: {
                        left: 100
                    },
                    xAxis: [
                        {
                            type: 'category',
                            data: ['1h', '3h', '5h', '7h', '9h', '11h', '13h', '15h', '17h', '19h', '21h', '23h'],
                            splitLine: {//显示分割线
                                show: true
                            }
                        }

                    ],
                    yAxis: [
                        {
                            type: 'value',
                            name: '温度',
                            axisLabel: {
                                formatter: '{value} ℃'
                            },
                            splitLine: {
                                show: false
                            }
                        }
                    ],
                    series: [
                        {
                            name: '温度',
                            type: 'line',
                            data:${detail.temperatures}
                        },
                    ]
                };
                // 使用刚指定的配置项和数据显示图表。
                chart.setOption(option);
                //两秒后关闭等待进度条
                setTimeout(function () {
                    chart.hideLoading();//隐藏等待条
                }, 2000);
                }
            });
        </script>
        <script>
            $(document).ready(function () {
                //放置图表的容器
                if(${detail.name eq '湿度传感器'}){
                    var group = $("#humidities");
                    //设置容器的宽度、高度和背景颜色
                    group.css({
                        "width": "100%",
                        "height": "45%",
                        "background-color": "aliceblue"
                    });
                    //创建图表对象
                    var chart = echarts.init(group.get(0));
                    chart.showLoading();//显示等待条
                    //设置图表显示的内容
                    var option = {
                        legend: {
                            selectedMode: false,//不可点击
                            data: ['湿度']
                        },
                        grid: {
                            left: 100
                        },
                        xAxis: [
                            {
                                type: 'category',
                                data: ['1h', '3h', '5h', '7h', '9h', '11h', '13h', '15h', '17h', '19h', '21h', '23h'],
                                splitLine: {//显示分割线
                                    show: true
                                }
                            }

                        ],
                        yAxis: [
                            {
                                type: 'value',
                                name: '湿度',
                                axisLabel: {
                                    formatter: '{value} %rh'
                                },
                                splitLine: {
                                    show: false
                                }
                            }
                        ],
                        series: [
                            {
                                name: '湿度',
                                type: 'line',
                                data:${detail.humidities}
                            },
                        ]
                    };
                    // 使用刚指定的配置项和数据显示图表。
                    chart.setOption(option);
                    //两秒后关闭等待进度条
                    setTimeout(function () {
                        chart.hideLoading();//隐藏等待条
                    }, 2000);
                }
            });
        </script>
        <script>
            $(document).ready(function () {
                //放置图表的容器
                if(${detail.name eq '树莓派cpu温度'}){
                    var group = $("#cputemps");
                    //设置容器的宽度、高度和背景颜色
                    group.css({
                        "width": "100%",
                        "height": "45%",
                        "background-color": "aliceblue"
                    });
                    //创建图表对象
                    var chart = echarts.init(group.get(0));
                    chart.showLoading();//显示等待条
                    //设置图表显示的内容
                    var option = {
                        legend: {
                            selectedMode: false,//不可点击
                            data: ['cpu温度']
                        },
                        grid: {
                            left: 100
                        },
                        xAxis: [
                            {
                                type: 'category',
                                data: ['1h', '3h', '5h', '7h', '9h', '11h', '13h', '15h', '17h', '19h', '21h', '23h'],
                                splitLine: {//显示分割线
                                    show: true
                                }
                            }

                        ],
                        yAxis: [
                            {
                                type: 'value',
                                name: 'cpu温度',
                                axisLabel: {
                                    formatter: '{value} ℃'
                                },
                                splitLine: {
                                    show: false
                                }
                            }
                        ],
                        series: [
                            {
                                name: 'cpu温度',
                                type: 'line',
                                data:${detail.cputemps}
                            },
                        ]
                    };
                    // 使用刚指定的配置项和数据显示图表。
                    chart.setOption(option);
                    //两秒后关闭等待进度条
                    setTimeout(function () {
                        chart.hideLoading();//隐藏等待条
                    }, 2000);
                }
            });
        </script>
        <c:choose>
            <c:when test="${detail.name eq '温度传感器'}">
                <div id="temperature"></div>
            </c:when>
            <c:when test="${detail.name eq '湿度传感器'}">
                <div id="humidities"></div>
            </c:when>
            <c:when test="${detail.name eq '树莓派cpu温度'}">
                <div id="cputemps"></div>
            </c:when>
        </c:choose>
    </div>
</div>
</body>
</html>
