<%--
  Created by IntelliJ IDEA.
  User: Wongnoubo
  Date: 2018/12/6
  Time: 15:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>找回用户名</title>
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
        <h3 class="logo-title" id="findusernamelogo" style="position: relative;left: 30%">找回用户名</h3>
    </div>
    <ul class="header-panel" style="position: relative;left: 60%;top: 3%">
        <li class="nav-first">
            <a href="admin_register.html">注册</a>
        </li>
        <li class="nav-first">
            <a href="login.html">登录</a>
        </li>
    </ul>
</nav>
<div class="panel panel-default" id="findloginuser" style="position: fixed;left:0%;width:100%;top:10%;height: 90%">
    <div class="panel-heading" style="background-color: #fff;position: relative;left: 30%;top:2%;width: 40%">
        <h3 class="panel-title">请输入你的注册邮箱找回你的账户名</h3>
    </div>
    <div class="panel-body" style="position: relative;left: 30%;top:2%;width: 40%">
        <form action="admin_findusername_do.html" method="post" id="findpassword">
        <div class="form-group" >
            <label for="adminemail">邮箱</label>
            <input type="text" class="form-control" name="adminemail" id="adminemail" placeholder="请输入注册邮箱">
        </div>
        <p style="text-align: right;color: #d3bfff;position: absolute" id="info"></p><br/>
            <input type="submit" value="找回用户名" class="btn btn-success btn-sm" class="text-left">
        </form>
        <script>
            function mySubmit(flag){
                return flag;
            }
            $("#findpassword").submit(function () {
                if($("#adminemail").val()==''){
                    alert("请填入完整注册邮箱信息！");
                    return mySubmit(false);
                }
            })
        </script>
    </div>
    <h6 style="position: relative;left: 45%;top: 69%">家+安全系统版权所有Copyright ©  2018-2018</h6>
    <h6 style="position: relative;left: 45.5%;top: 70%">联系方式：jiajiasensorsystem@163.com</h6>
</div>
</body>
</html>
