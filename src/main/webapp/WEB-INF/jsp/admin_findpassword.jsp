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
    <link href="static/images/question-mark-outline.png" rel="shortcut icon">
    <title>找回密码</title>
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
        <h3 class="logo-title" id="findusernamelogo" style="position: relative;left: 30%">找回密码</h3>
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
<div class="panel panel-default" id="findloginpassword" style="position: fixed;left:0%;width:100%;top:10%;height: 90%">
    <div class="panel-heading" style="background-color: #fff;position: relative;left: 30%;top:2%;width: 40%">
        <h3 class="panel-title">请输入你的注册邮箱找回你的密码</h3>
    </div>
    <div class="panel-body" style="position: relative;left: 30%;top:2%;width: 40%">
        <a id="findadminpassword">帮助</a>
        <script>
            document.getElementById("findadminpassword").onclick = function () {
                alert("请输入注册所用的邮箱，用于找回密码。注意邮箱的格式");
            }
        </script>
        <form action="admin_findpassword_do.html" method="post" id="findpassword">
        <div class="input-group" >
            <label for="adminemail">邮箱</label>
            <input type="email" class="form-control" name="adminemail" id="adminemail" placeholder="请输入注册邮箱">
        </div>
        <p style="text-align: right;color: #d3bfff;position: absolute" id="info"></p><br/>
            <input type="submit" value="找回密码" class="btn btn-success btn-sm" class="text-left" onclick="check()">
            <script>
                function check(){
                    var reg = new RegExp("^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$"); //正则表达式
                    var obj = document.getElementById("adminemail"); //要验证的对象
                    if(obj.value === ""){ //输入不能为空
                        obj.value = "";
                        alert("输入不能为空!请输入正确的邮箱信息");
                        return false;
                    }else if(!reg.test(obj.value)){ //正则验证不通过，格式不对
                        alert("验证不通过，请输入正确的邮箱。");
                        obj.value = "";
                        return false;
                    }else{
                        alert("邮箱通过校验！");
                        return true;
                    }
                }
            </script>
        </form>
    </div>
    <h6 style="position: relative;left: 45%;top: 69%">家+安全系统版权所有Copyright ©  2018-2018</h6>
    <h6 style="position: relative;left: 45.5%;top: 70%">联系方式：jiajiasensorsystem@163.com</h6>
</div>
</body>
</html>
