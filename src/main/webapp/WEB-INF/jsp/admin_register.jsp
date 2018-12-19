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
    <link href="static/images/person-add-outline.png" rel="shortcut icon">
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
        <form action="admin_register_do.html" method="post" id="registeradmin">
            <label for="emailid">邮箱</label>
            <input type="text" class="form-control" id="emailid" name="emailid" placeholder="请输入邮箱">
            <label for="nickname">用户昵称</label>
            <input type="text" class="form-control" id="nickname" name="nickname" placeholder="请用户昵称">
            <label for="passwordid">密码</label>
            <input type="password" class="form-control" id="passwordid" name="passwordid" placeholder="请输入密码">
            <label for="repasswordid">再次输入密码</label>
            <input type="password" class="form-control" id="repasswordid" name="repasswordid" placeholder="请再次输入密码" onkeyup="validate()">
            <em id="tishi" style="color: red"></em>
            <p style="text-align: right;color: #d3bfff;position: absolute" id="info"></p><br/>
            <input type="submit" value="注册用户" class="btn btn-success btn-sm" class="text-left">
        </form>
        <script>
            function check(){
                var reg = new RegExp("^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$"); //正则表达式
                var obj = document.getElementById("emailid"); //要验证的对象
                if(obj.value === ""){ //输入不能为空
                    obj.value = "";
                    alert("输入的邮箱不能为空!");
                    return false;
                }else if(!reg.test(obj.value)){ //正则验证不通过，格式不对
                    obj.value = "";
                    alert("验证不通过，请输入正确的邮箱。");
                    return false;
                }else{
                    alert("邮箱通过校验！");
                    return true;
                }
            }

            function mySubmit(flag){
                return flag;
            }
            $("#registeradmin").submit(function () {
                if($("#emailid").val()==''||$("#passwordid").val()==''||$("#repasswordid").val()==''||$("#nickname").val()==''){
                    alert("请填入完整注册信息！");
                    return mySubmit(false);
                }
            })

            $("#registeradmin").submit(function () {
                check();
            })

            function validate() {
                var pwd1 = document.getElementById("passwordid").value;
                var pwd2 = document.getElementById("repasswordid").value;
                <!-- 对比两次输入的密码 -->
                if(pwd1 == pwd2) {
                    document.getElementById("tishi").innerHTML="<font color='green'>两次密码相同</font>";
                    document.getElementById("submit").disabled = false;
                }
                else {
                    document.getElementById("tishi").innerHTML="<font color='red'>两次密码不相同</font>";
                    document.getElementById("submit").disabled = true;
                }
            }
        </script>
    </div>
    <div class="form-group" >
    </div>
    <h6 style="position: relative;left: 45%;top: 46%">家+安全系统版权所有Copyright ©  2018-2018</h6>
    <h6 style="position: relative;left: 45.5%;top: 47%">联系方式：jiajiasensorsystem@163.com</h6>
</div>
</body>
</html>
