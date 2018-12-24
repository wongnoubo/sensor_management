<%--
  Created by IntelliJ IDEA.
  User: Wongnoubo
  Date: 2018/11/19
  Time: 21:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <%
        request.setCharacterEncoding("utf-8");
    %>
    <link href="static/images/plus-outline.png" rel="shortcut icon">
    <title>传感器信息添加</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <script src="js/jquery-3.2.1.js"></script>
    <script src="js/bootstrap.min.js" ></script>
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
                        <li><a href="sensor_add.html?adminId=${admin.adminId}">增加传感器</a></li>
                    </ul>
                </li>
                <li >
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
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
                        <li><a href="adminvideo.html">实时监控</a></li>
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

<div style="position: relative;top: 10%;width: 80%;margin-left: 10%">
    <a id="addsensorhelp">帮助</a>
    <script>
        document.getElementById("addsensorhelp").onclick = function () {
            alert("请按照一定规范来命名：温度传感器，湿度传感器，树莓派cpu温度，有毒气体传感器，红外人体传感器");
        }
    </script>
    <form action="sensor_add_do.html?adminId=${admin.adminId}" method="post" id="addsensor" >
        <div class="form-group">
            <label for="sensorName">传感器名</label>
            <input type="text" class="form-control" name="sensorName" id="sensorName" placeholder="请输入传感器名">
        </div>
        <div class="form-group">
            <label for="sensorAddress">位置</label>
            <input type="text" class="form-control" name="sensorAddress" id="sensorAddress"  placeholder="请输入位置">
        </div>
        <div class="form-group">
            <label for="sensorIntroduction">简介</label>
            <textarea class="form-control" rows="3"  name="sensorIntroduction" id="sensorIntroduction" placeholder="请输入简介"></textarea>
        </div>
        <div class="form-group">
            <label for="sensorPrice">价格(单位：元)</label>
            <input type="text" class="form-control"  name="sensorPrice"  id="sensorPrice" placeholder="请输入价格">
        </div>
        <div class="form-group">
            <label for="stateselect">状态</label>
                <select class="form-control" name="stateselect"id="stateselect" placeholder="请输入传感器状态">
                    <option value="1" id="sensorState1" style="background-color:#10adff">正常工作</option>
                    <option value="0" id="sensorState2" style="background-color: #ff1a15">异常</option>
                </select>
        </div>
        <input type="submit" value="添加" class="btn btn-success btn-sm" class="text-left">
        <script>
            function mySubmit(flag){
                return flag;
            }
            $("#addsensor").submit(function () {
                if($("#sensorName").val()==''||$("#sensorIntroduction").val()==''||$("#sensorPrice").val()==''||$("#sensorId").val()==''||$("#sensorState").val()==''){
                    alert("请填入完整传感器信息！");
                    return mySubmit(false);
                }else{
                    if($("#sensorName").val()=='温度传感器'||$("#sensorName").val()=='湿度传感器'||$("#sensorName").val()=='树莓派cpu温度'||$("#sensorName").val()=='有毒气体传感器'||$("#sensorName").val()=='红外人体传感器'){
                        return mySubmit(true);
                    }else{
                        alert("传感器的名字不符合规范，请按照一定规范来命名：温度传感器，湿度传感器，树莓派cpu温度，有毒气体传感器，红外人体传感器")
                        return mySubmit(false);
                    }
                }
            })
        </script>
    </form>
</div>
</body>
</html>
