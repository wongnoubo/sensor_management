<%--
  Created by IntelliJ IDEA.
  User: Wongnoubo
  Date: 2018/12/9
  Time: 14:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>个人信息</title>
</head>
<body>
<div class="col-xs-6 col-md-offset-3" style="position: relative;top: 10%">
    <div class="panel panel-primary" style="width: 60%;margin-left: 20%">
        <div class="panel-heading" style="background-color: #fff">
            <h3 class="panel-title">
                管理员信息
            </h3>
        </div>
        <div class="panel-body">
            <table class="table table-hover">
                <tr>
                    <th width="15%">用户名</th>
                    <td>${admininformation.adminId}</td>
                </tr>
                <tr>
                    <th width="15%">昵称</th>
                    <td>${admininformation.nickname}</td>
                </tr>
                <tr>
                    <th width="15%">邮箱</th>
                    <td>${admininformation.email}</td>
                </tr>
                <tr>
                    <th width="25%">对应的数据表</th>
                    <td>${admininformation.infotablename}</td>
                </tr>
            </table>
        </div>
    </div>
</div>
</body>
</html>
