<%--
  Created by IntelliJ IDEA.
  User: Wongnoubo
  Date: 2018/12/13
  Time: 13:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link href="${pageContext.request.contextPath}/static/images/video-outline.png" rel="shortcut icon">
    <title>视频监控</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/static/js/jquery-3.2.1.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/bootstrap.min.js" ></script>
    <script>
        function display() {
            var date = new Date();
            var year = date.getFullYear();
            var month = date.getMonth()+1;
            if(month<10){
                month = "0"+month;
            }
            var mydate = date.getDate();
            var hours = date.getHours();
            var mins = date.getMinutes();
            if(mins<10){
                mins="0"+mins;
            }
            var secs = date.getSeconds();
            if(secs<10){
                secs = "0"+secs;
            }

            var time = year + "-" + month +"-"+mydate + " "+ hours +":"+ mins +":"+secs;
            document.getElementById("time").innerHTML = time;
            setTimeout("display()",1000);
            return time;
        }

        window.onload = function () {
            display();
        };
    </script>
    <style type="text/css">
        *{
            margin: auto;
        }
        div{
            margin: auto;
        }
        #camera{
            width: 640px;
            height: 320px;
            background-color:#f0f0f0;
        }
        .con{
            width: 640px;
        }
        #time{
            font-size: 20px;
            float: right;
            background-color: #f0f0f0;
        }
        #btn {
            margin-top: 10px;
        }
    </style>
</head>
<body>
<nav  style="position:relative;z-index: 999;width: 100%;background-color: #fff" class="navbar navbar-default" role="navigation" >
    <div class="container-fluid">
        <div class="navbar-header" style="margin-left: 8%;margin-right: 1%">
            <a class="navbar-brand" href="/admin_main.html">家+安全系统</a>
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
                        <li><a href="sensor_add.html?adminId=${admin.adminId}">增加传感器</a></li>
                    </ul>
                </li>
                <li >
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        密码修改
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="/admin_repasswd.html">密码修改</a></li>
                    </ul>
                </li>
                <li >
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" >
                        实时监控
                        <b class="caret"></b>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="adminvideo.html">实时监控</a></li>
                    </ul>
                </li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="/admininformation?adminId=${admin.adminId}"><span class="glyphicon glyphicon-user"></span>&nbsp;${admin.nickname}，已登录</a></li>
                <li><a href="/logout.html"><span class="glyphicon glyphicon-log-in"></span>&nbsp;退出</a></li>
            </ul>
        </div>
    </div>
</nav>
<div id="camera" style="position: relative;background-color:#f0f0f0">
    <div id="header" class="con" style="position: relative">
        <span id="time">Time</span>
    </div>
    <img src="http://192.168.0.100:8090/?action=stream" width="640px"; height="480px";/>
    <%--<button class="btn-info center-block btn-lg" id="btn" onclick="add()">切换摄像头</button>--%>
</div>
</body>
</html>
