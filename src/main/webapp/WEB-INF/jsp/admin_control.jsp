<%--
  Created by IntelliJ IDEA.
  User: Wongnoubo
  Date: 2019/3/2
  Time: 10:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="zh-CN">
<head>
    <link href="static/images/control.png" rel="shortcut icon">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>远程控制</title>
    <script src="js/jquery-3.2.1.js" type="text/javascript"></script>
    <script src="js/bootstrap.min.js" type="text/javascript"></script>
    <link href="css/bootstrap.css" rel="stylesheet" type="text/css" />
    <link href="css/font-awesome-4.7.0/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
    <style type="text/css">
        .page-header { margin: 20px 0; border-bottom: 1px solid #eee; padding-bottom: 0; text-align: center; }
        .btn-item { text-align: center; }
        i { margin-right: 3px; display: inline-block; }
        h1 { text-align: center; }
        .tip { font-weight: bold; color: black; }
        .lead { font-size: small; }
        th{
            text-align:center;/** 设置水平方向居中 */
            vertical-align:middle/** 设置垂直方向居中 */
        }
        td{
             text-align:center;/** 设置水平方向居中 */
             vertical-align:middle/** 设置垂直方向居中 */
         }

    </style>
</head>
<body>
<nav  style="position:fixed;z-index: 999;width: 100%;background-color: #fff" class="navbar navbar-default" role="navigation" >
<div class="container-fluid">
    <div class="navbar-header" style="margin-left: 8%;margin-right: 1%">
        <a class="navbar-brand" href="admin_main.html")>安居系统</a>
    </div>
    <div class="collapse navbar-collapse" >
        <ul class="nav navbar-nav navbar-left">
            <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                    传感器管理
                    <b class="caret"></b>
                </a>
                <ul class="dropdown-menu">
                    <li><a href="allsensors?adminId=${admin.adminId}">全部传感器</a></li>
                    <li class="divider"></li>
                    <li><a href="sensor_add?adminId=${admin.adminId}">增加传感器</a></li>
                </ul>
            </li>
            <li >
                <a href="#" class="dropdown-toggle" data-toggle="dropdown" >
                    密码修改
                    <b class="caret"></b>
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
                    <li><a href="adminvideo">实时监控</a></li>
                    <li class="divider"></li>
                    <li><a href="admin_control">远程控制</a></li>
                </ul>
            </li>
        </ul>
        <ul class="nav navbar-nav navbar-right">
            <li><a href="admininformation?adminId=${admin.adminId}"><span class="glyphicon glyphicon-user" aria-hidden="true"></span>&nbsp;${admin.nickname}，已登录</a></li>
            <li><a href="logout.html"><span class="glyphicon glyphicon-log-in"></span>&nbsp;退出</a></li>
        </ul>
    </div>
</div>
</nav>
<div class="container" style="position: relative;top: 10%;width: 80%;margin-left: 8%">
    <div class="page-header">
        <h3>远程控制中心</h3>
        <p class="lead">
            用于控制连接到树莓派上的家电
        </p>
    </div>
    <div class="panel panel-primary">
        <div class="panel-heading">
            设备</div>
        <div class="panel-body">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>空调</th>
                    <th>卧室灯</th>
                    <th>排气扇</th>
                    <th>冰箱</th>
                    <th>电饭煲</th>
                    <th>加湿器</th>
                    <th>窗帘</th>
                    <th>电视机</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>
                        <button type="button" class="btn btn-primary btn-trigger" id="kongtiaokai">
                            <i class="fa fa-refresh"></i>空调开</button>
                    </td>
                    <td>
                        <button type="button" class="btn btn-primary btn-trigger" id="woshidengkai">
                            <i class="fa fa-refresh"></i>卧室灯开</button>
                    </td>
                    <td>
                        <button type="button" class="btn btn-primary btn-trigger" id="paiqishankai">
                            <i class="fa fa-refresh"></i>排气扇开</button>
                    </td>
                    <td>
                        <button type="button" class="btn btn-primary btn-trigger" id="bingxinagkai">
                            <i class="fa fa-refresh"></i>冰箱开</button>
                    </td>
                    <td>
                    <button type="button" class="btn btn-primary btn-trigger" id="dianfanbaokai">
                        <i class="fa fa-refresh"></i>电饭煲开</button>
                    </td>
                    <td>
                        <button type="button" class="btn btn-primary btn-trigger" id="jiashiqikai">
                            <i class="fa fa-refresh"></i>加湿器开</button>
                    </td>
                    <td>
                        <button type="button" class="btn btn-primary btn-trigger" id="chuangliankai">
                            <i class="fa fa-refresh"></i>窗帘开</button>
                    </td>
                    <td>
                        <button type="button" class="btn btn-primary btn-trigger" id="dianshijikai">
                            <i class="fa fa-refresh"></i>电视机开</button>
                    </td>
                </tr>
                <tr>
                    <td>
                    <button type="button" class="btn btn-danger btn-trigger" id="kongtiaoguan">
                        <i class="fa fa-power-off"></i>空调关</button>
                    </td>
                    <td>
                        <button type="button" class="btn btn-danger btn-trigger" id="woshidengguan">
                            <i class="fa fa-power-off"></i>卧室灯关</button>
                    </td>
                    <td>
                        <button type="button" class="btn btn-danger btn-trigger" id="paiqishanguan">
                            <i class="fa fa-power-off"></i>排气扇关</button>
                    </td>
                    <td>
                        <button type="button" class="btn btn-danger btn-trigger" id="bingxiangguan">
                            <i class="fa fa-power-off"></i>冰箱关</button>
                    </td>
                    <td>
                        <button type="button" class="btn btn-danger btn-trigger" id="dianfanbaoguan">
                            <i class="fa fa-power-off"></i>电饭煲关</button>
                    </td>
                    <td>
                        <button type="button" class="btn btn-danger btn-trigger" id="jiashiqiguan">
                            <i class="fa fa-power-off"></i>加湿器关</button>
                    </td>
                    <td>
                        <button type="button" class="btn btn-danger btn-trigger" id="chuanglianguan">
                            <i class="fa fa-power-off"></i>窗帘关</button>
                    </td>
                    <td>
                        <button type="button" class="btn btn-danger btn-trigger" id="dianshijiguan">
                            <i class="fa fa-power-off"></i>电视机关</button>
                    </td>
                </tr>
                </tbody>
            </table>
            <script type="text/javascript">
                $(function () {
                    $(".btn-trigger").click(function (){
                        var text = $(this).text().replace(/ /g, "").replace(/\n/g, "").replace(/\r/g, "").replace(/\t/g, "");
                        var cmd = "";
                        switch(text){
                            case "空调开":
                                cmd = "空调开";
                                break;
                            case "空调关":
                                cmd = "空调关";
                                break;
                            case "卧室灯开":
                                cmd = "卧室灯开";
                                break;
                            case "卧室灯关":
                                cmd = "卧室灯关";
                                break;
                            case "排气扇开":
                                cmd = "排气扇开";
                                break;
                            case "排气扇关":
                                cmd = "排气扇关";
                                break;
                            case "冰箱开":
                                cmd = "冰箱开";
                                break;
                            case "冰箱关":
                                cmd = "冰箱关";
                                break;
                            case "电饭煲开":
                                cmd = "电饭煲开";
                                break;
                            case "电饭煲关":
                                cmd = "电饭煲关";
                                break;
                            case "加湿器开":
                                cmd = "加湿器开";
                                break;
                            case "加湿器关":
                                cmd = "加湿器关";
                                break;
                            case "窗帘开":
                                cmd = "窗帘开";
                                break;
                            case "窗帘关":
                                cmd = "窗帘关";
                                break;
                            case "电视机开":
                                cmd = "电视机开";
                                break;
                            case "电视机关":
                                cmd = "电视机关";
                                break;
                        }
                        if(confirm("确定要执行该命令吗？")){
                            $.ajax({
                                type:"POST",
                                url:"admin_controldo",
                                data:{
                                    action:"set-linux-cmd",
                                    cmd:cmd,
                                },
                                success: function(result){
                                    alert("命令执行成功！");
                                }
                            })
                        }
                    });
                })
            </script>
        </div>
    </div>
</div>
</body>
</html>

