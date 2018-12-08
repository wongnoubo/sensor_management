<%--
  Created by IntelliJ IDEA.
  User: Wongnoubo
  Date: 2018/12/7
  Time: 22:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String Code = "";
    if(request.getParameter("code")!=null)
        Code=request.getParameter("code");
%>
<html>
<head>
    <title>激活新用户页面</title>
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
        <h3 class="logo-title" id="findusernamelogo" style="position: relative;left: 30%">激活用户</h3>
    </div>
    <ul class="header-panel" style="position: relative;left: 60%;top: 3%">
        <li class="nav-first">
            <a href="login.html">登录</a>
        </li>
    </ul>
    <div class="panel-heading" style="background-color: #fff;position: relative;left: 30%;top:2%;width: 40%">
        <h3 class="panel-title">点击按钮激活新用户</h3>
    </div>
    <div class="panel-body" style="position: relative;left: 30%;top:2%;width: 40%">
        <form action="welcome_do.html" method="post" id="welcomeCode">
            <input type="hidden" id="Code" name = "Code">
            <input type="submit" value="激活用户" class="btn btn-success btn-sm" class="text-left" onclick="Jsp_Get_Js()">
        </form>
    </div>
</nav>
<script>
    function Jsp_Get_Js()
    {
        //拿取jsp中嵌入的java值
        var Code ="<%=Code%>"
        document.getElementById("Code").value = Code;
        //在此将JS中的v变量的值交给JSP
        document.Isform.submit();
    }
</script>
</body>
</html>
