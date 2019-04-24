<%--
  Created by IntelliJ IDEA.
  User: Wongnoubo
  Date: 2018/11/19
  Time: 21:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link href="${pageContext.request.contextPath}/static/images/eye-outline.png" rel="shortcut icon">
    <title>全部传感器信息</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/static/js/jquery-3.2.1.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/bootstrap.min.js" ></script>
    <script>
        //异步刷新某个参数，不会有跳跃感
        $(function () {
            $("#timeStamp").load(allsensors)
            
        });
        function res(param){
            $.ajax({
                url :'allsensors',
                data :{aItemId: param},
                success: function(data){
                    console.log("huanglei"+data);
                }
            });
        }

    </script>
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
                        <li><a href="adminvideo">实时监控</a></li>
                        <li class="divider"></li>
                        <li><a href="http://jiajiasystem.vipgz1.idcfengye.com">远程控制</a></li>
                    </ul>
                </li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li>
                    <a href="/admininformation?adminId=${admin.adminId}"><span class="glyphicon glyphicon-user"></span>&nbsp;${admin.nickname}，已登录</a>
                </li>
                <li><a href="/logout.html"><span class="glyphicon glyphicon-log-in"></span>&nbsp;退出</a></li>
            </ul>
        </div>
    </div>
</nav>


<div style="padding: 70px 550px 10px">
    <form   method="post" action="querysensor?adminId=${admin.adminId}" class="form-inline"  id="searchform">
        <div class="input-group">
            <input type="text" placeholder="输入传感器名" class="form-control" id="search" name="searchWord" class="form-control">
            <span class="input-group-btn">
                            <input type="submit" value="搜索" class="btn btn-default">
            </span>
        </div>
    </form>
    <script>
        function mySubmit(flag){
            return flag;
        }
        $("#searchform").submit(function () {
            var val=$("#search").val();
            if(val==''){
                alert("请输入关键字");
                return mySubmit(false);
            }
        })
    </script>
</div>
<div style="position: relative;top: 10%" id="alertinfo">
    <c:if test="${!empty succ}">
        <div class="alert alert-success alert-dismissable">
            <button type="button" class="close" data-dismiss="alert"
                    aria-hidden="true">
                &times;
            </button>
                ${succ}
        </div>
    </c:if>
    <c:if test="${!empty error}">
        <div class="alert alert-danger alert-dismissable">
            <button type="button" class="close" data-dismiss="alert"
                    aria-hidden="true">
                &times;
            </button>
                ${error}
        </div>
    </c:if>
</div>
<div class="panel panel-default" style="width: 90%;margin-left: 5%">
    <div class="panel-heading" style="background-color: #fff">
    <h3 class="panel-title">
        全部传感器
    </h3>
</div>
    <div class="panel-body">
        <table class="table table-hover">
            <thead>
            <tr>
                <th>传感器名</th>
                <th>读数</th>
                <th>采样时间</th>
                <th>位置</th>
                <th>详情</th>
                <th>编辑</th>
                <th>删除</th>
                <th>导出</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${sensors}" var="sensor">
                <tr>
                    <td><c:out value="${sensor.name}"></c:out></td>
                    <td><c:choose>
                        <c:when test="${sensor.name eq '温度传感器'}">
                            <c:out value="${sensor.temperature}℃"></c:out>
                        </c:when>
                        <c:when test="${sensor.name eq '湿度传感器'}">
                            <c:out value="${sensor.humidity}%rh"></c:out>
                        </c:when>
                        <c:when test="${sensor.name eq '树莓派cpu温度'}">
                            <c:out value="${sensor.cputemp}℃"></c:out>
                        </c:when>
                        <c:when test="${sensor.name eq '红外人体传感器' && sensor.humenState==1}">
                            <c:out value="有人经过"></c:out>
                        </c:when>
                        <c:when test="${sensor.name eq '红外人体传感器' && sensor.humenState==0}">
                            <c:out value="无人经过"></c:out>
                        </c:when>
                        <c:when test="${sensor.name eq '有毒气体传感器' && sensor.toxicAirState==0}">
                            <c:out value="有毒气体浓度正常"></c:out>
                        </c:when>
                        <c:when test="${sensor.name eq '有毒气体传感器' && sensor.toxicAirState==1}">
                            <c:out value="有毒气体浓度异常"></c:out>
                        </c:when>
                    </c:choose>
                    </td>
                    <td id="timeStamp"><c:out value="${sensor.timeStamp}"></c:out></td>
                    <td><c:out value="${sensor.sensorAddress}"></c:out></td>
                    <td><a href="sensordetail?sensorId=<c:out value="${sensor.id}"></c:out>&adminId=<c:out value="${admin.adminId}"></c:out>"><button type="button" class="btn btn-success btn-xs">详情</button></a></td>
                    <td><a href="updatesensor?sensorId=<c:out value="${sensor.id}"></c:out>&adminId=<c:out value="${admin.adminId}"></c:out>"><button type="button" class="btn btn-info btn-xs">编辑</button></a></td>
                    <td><a href="deletesensor?sensorId=<c:out value="${sensor.id}"></c:out>&adminId=<c:out value="${admin.adminId}"></c:out>"><button type="button" class="btn btn-danger btn-xs">删除</button></a></td>
                    <td><a href="exportExcelFile?sensorId=<c:out value="${sensor.id}"></c:out>&adminId=<c:out value="${admin.adminId}"></c:out>"><button type="button" class="btn btn-primary btn-xs">导出</button></a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
