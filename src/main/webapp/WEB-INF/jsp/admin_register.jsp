<%--
  Created by IntelliJ IDEA.
  User: Wongnoubo
  Date: 2018/12/6
  Time: 0:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>注册用户</title>
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
<nav style="position:fixed;z-index: 999;width: 100%;background-color: #f0f0f0" class="navbar navbar-default" role="navigation" >
    <div>
        <h3 class="logo-title" id="findusernamelogo" style="position: relative;left: 30%">注册用户</h3>
    </div>
    <ul class="header-panel" style="position: relative;left: 60%;top: 3%">
        <li class="nav-first">
            <a href="login.html">登录</a>
        </li>
    </ul>
</nav>
<div class="panel panel-default" id="findloginpassword" style="position: fixed;left:0%;width:100%;top:10%;height: 90%">
    <div class="panel-heading" style="background-color: #fff;position: relative;left: 30%;top:2%;width: 40%">
        <h3 class="panel-title">请输入相关信息注册用户</h3>
    </div>
    <div class="panel-body" style="position: relative;left: 30%;top:2%;width: 40%">
        <div class="form-group" >
            <label for="emailid">邮箱</label>
            <input type="text" class="form-control" id="emailid" placeholder="请输入邮箱">
            <label for="userid">用户名</label>
            <input type="text" class="form-control" id="userid" placeholder="请输入用户名">
            <label for="passwordid">密码</label>
            <input type="password" class="form-control" id="passwordid" placeholder="请输入密码">
            <label for="repasswordid">再次输入密码</label>
            <input type="password" class="form-control" id="repasswordid" placeholder="请再次输入密码">
        </div>
        <p style="text-align: right;color: #d3bfff;position: absolute" id="info"></p><br/>
        <button id="loginButton"  class="btn btn-primary  btn-block" style="position: relative;left: 0%;width: 15%">注册用户
        </button>
    </div>
    <h6 style="position: relative;left: 45%;top: 46%">家+安全系统版权所有Copyright ©  2018-2018</h6>
    <h6 style="position: relative;left: 45.5%;top: 47%">联系方式：jiajiasensorsystem@163.com</h6>
</div>
</body>
</html>
