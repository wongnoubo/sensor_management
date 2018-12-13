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
    <title>视频监控</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <script src="js/jquery-3.2.1.js"></script>
    <script src="js/bootstrap.min.js" ></script>
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
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        密码修改
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href=admin_repasswd.html">密码修改</a></li>
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
<div id="content">
    <img src="http://192.168.0.26:8090/?action=stream" width="640px"; height="480px";/>
</div>
</body>
</html>
