<%--
  Created by IntelliJ IDEA.
  User: Wongnoubo
  Date: 2018/11/19
  Time: 21:07
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8"%>
<html>
<head>
    <link href="${pageContext.request.contextPath}/static/images/bdzj.png" rel="shortcut icon">
    <%
        request.setCharacterEncoding("utf-8");
    %>
    <title>编辑${detail.name}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/static/js/jquery-3.2.1.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/bootstrap.min.js" ></script>
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

<div class="col-xs-6 col-md-offset-3" style="position: relative;top: 10%">
    <div class="panel panel-primary">
        <div class="panel-heading">
            <h3 class="panel-title">编辑传感器——${detail.name}</h3>
        </div>
        <div class="panel-body">
            <form action="sensor_edit_do?id=${detail.id}&adminId=${admin.adminId}" method="post" id="editsensor" >

                <div class="input-group">
                    <span  style="width:80px;height:30px" class="input-group-addon">传感器</span>
                    <input type="text" class="form-control" name="name" id="name" value="${detail.name}">
                </div>
                <div class="input-group">
                    <span  style="width:80px;height:30px" class="input-group-addon">价格(元)</span>
                    <input type="text" class="form-control" name="price"  id="price" value="${detail.price}">
                </div>
                <div class="input-group">
                    <span style="width:80px;height:30px" class="input-group-addon">位置</span>
                    <input type="text" class="form-control" name="sensorAddress" id="sensorAddress" value="${detail.sensorAddress}" >
                </div>
                <div class="input-group">
                    <span  style="width:80px;height:30px" class="input-group-addon">简介</span>
                    <input type="text" class="form-control" name="sensorIntroduction" id="sensorIntroduction"  value="${detail.sensorIntroduction}" >
                </div>
                <div class="input-group">
                    <span  style="width:80px;height:30px" class="input-group-addon">状态</span>
                    <c:choose>
                        <c:when test="${detail.sensorState eq 1}">
                           <select class="form-control" id="sensorState" name="sensorState" placeholder="请输入传感器状态">
                                <option value="1" id="sensorState1" style="background-color:#10adff">正常工作</option>
                                <option value="0" id="sensorState2" style="background-color: #ff1a15">异常</option>
                            </select>
                        </c:when>
                        <c:when test="${detail.sensorState eq 0}">
                            <select class="form-control" id="sensorState" name="sensorState" placeholder="请输入传感器状态">
                                <option value="0" id="sensorState4" style="background-color: #ff1a15">异常</option>
                                <option value="1" id="sensorState3" style="background-color:#10adff">正常工作</option>
                            </select>
                        </c:when>
                    </c:choose>
                </div>
                <input type="submit" value="确定" class="btn btn-success btn-sm" class="text-left">
                <script>
                    function mySubmit(flag){
                        return flag;
                    }
                    $("#addsensor").submit(function () {
                        if($("#name").val()==''||$("#sensorIntroduction").val()==''||$("#price").val()==''||$("#id").val()==''||$("#sensorState").val()==''){
                            alert("请填入完整传感器信息！");
                            return mySubmit(false);
                        }
                    })
                </script>
            </form>
        </div>
    </div>
</div>

</body>
</html>
