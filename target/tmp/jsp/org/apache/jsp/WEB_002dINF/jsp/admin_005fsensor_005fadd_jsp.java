/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: jetty/9.4.14.v20181114
 * Generated at: 2018-12-19 14:24:37 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.WEB_002dINF.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class admin_005fsensor_005fadd_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = null;
  }

  private volatile javax.el.ExpressionFactory _el_expressionfactory;
  private volatile org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public java.util.Set<java.lang.String> getPackageImports() {
    return _jspx_imports_packages;
  }

  public java.util.Set<java.lang.String> getClassImports() {
    return _jspx_imports_classes;
  }

  public javax.el.ExpressionFactory _jsp_getExpressionFactory() {
    if (_el_expressionfactory == null) {
      synchronized (this) {
        if (_el_expressionfactory == null) {
          _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
        }
      }
    }
    return _el_expressionfactory;
  }

  public org.apache.tomcat.InstanceManager _jsp_getInstanceManager() {
    if (_jsp_instancemanager == null) {
      synchronized (this) {
        if (_jsp_instancemanager == null) {
          _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
        }
      }
    }
    return _jsp_instancemanager;
  }

  public void _jspInit() {
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
      throws java.io.IOException, javax.servlet.ServletException {

    final java.lang.String _jspx_method = request.getMethod();
    if (!"GET".equals(_jspx_method) && !"POST".equals(_jspx_method) && !"HEAD".equals(_jspx_method) && !javax.servlet.DispatcherType.ERROR.equals(request.getDispatcherType())) {
      response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "JSPs only permit GET POST or HEAD");
      return;
    }

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("    <link href=\"static/images/plus-outline.png\" rel=\"shortcut icon\">\r\n");
      out.write("    <title>传感器信息添加</title>\r\n");
      out.write("    <link rel=\"stylesheet\" href=\"css/bootstrap.min.css\">\r\n");
      out.write("    <script src=\"js/jquery-3.2.1.js\"></script>\r\n");
      out.write("    <script src=\"js/bootstrap.min.js\" ></script>\r\n");
      out.write("    <style>\r\n");
      out.write("        body{\r\n");
      out.write("            background-color: rgb(240,242,245);\r\n");
      out.write("        }\r\n");
      out.write("    </style>\r\n");
      out.write("\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("<nav  style=\"position:fixed;z-index: 999;width: 100%;background-color: #fff\" class=\"navbar navbar-default\" role=\"navigation\" >\r\n");
      out.write("    <div class=\"container-fluid\">\r\n");
      out.write("        <div class=\"navbar-header\" style=\"margin-left: 8%;margin-right: 1%\">\r\n");
      out.write("            <a class=\"navbar-brand\" href=\"admin_main.html\">家+安全系统</a>\r\n");
      out.write("        </div>\r\n");
      out.write("        <div class=\"collapse navbar-collapse\" >\r\n");
      out.write("            <ul class=\"nav navbar-nav navbar-left\">\r\n");
      out.write("                <li class=\"dropdown\">\r\n");
      out.write("                    <a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\">\r\n");
      out.write("                        传感器管理\r\n");
      out.write("                        <b class=\"caret\"></b>\r\n");
      out.write("                    </a>\r\n");
      out.write("                    <ul class=\"dropdown-menu\">\r\n");
      out.write("                        <li><a href=\"allsensors.html?adminId=");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${admin.adminId}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("\">全部传感器</a></li>\r\n");
      out.write("                        <li class=\"divider\"></li>\r\n");
      out.write("                        <li><a href=\"sensor_add.html\">增加传感器</a></li>\r\n");
      out.write("                    </ul>\r\n");
      out.write("                </li>\r\n");
      out.write("                <li >\r\n");
      out.write("                    <a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\">\r\n");
      out.write("                        密码修改\r\n");
      out.write("                    </a>\r\n");
      out.write("                    <ul class=\"dropdown-menu\">\r\n");
      out.write("                        <li><a href=\"admin_repasswd.html\">密码修改</a></li>\r\n");
      out.write("                    </ul>\r\n");
      out.write("                </li>\r\n");
      out.write("                <li >\r\n");
      out.write("                    <a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\" >\r\n");
      out.write("                        实时监控\r\n");
      out.write("                        <b class=\"caret\"></b>\r\n");
      out.write("                    </a>\r\n");
      out.write("                    <ul class=\"dropdown-menu\">\r\n");
      out.write("                        <li><a href=\"adminvideo.html\">实时监控</a></li>\r\n");
      out.write("                    </ul>\r\n");
      out.write("                </li>\r\n");
      out.write("            </ul>\r\n");
      out.write("            <ul class=\"nav navbar-nav navbar-right\">\r\n");
      out.write("                <li><a href=\"admininformation?adminId=");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${admin.adminId}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("\"><span class=\"glyphicon glyphicon-user\"></span>&nbsp;");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${admin.nickname}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("，已登录</a></li>\r\n");
      out.write("                <li><a href=\"logout.html\"><span class=\"glyphicon glyphicon-log-in\"></span>&nbsp;退出</a></li>\r\n");
      out.write("            </ul>\r\n");
      out.write("        </div>\r\n");
      out.write("    </div>\r\n");
      out.write("</nav>\r\n");
      out.write("\r\n");
      out.write("<div style=\"position: relative;top: 10%;width: 80%;margin-left: 10%\">\r\n");
      out.write("    <a id=\"addsensorhelp\">帮助</a>\r\n");
      out.write("    <script>\r\n");
      out.write("        document.getElementById(\"addsensorhelp\").onclick = function () {\r\n");
      out.write("            alert(\"请按照一定规范来命名：温度传感器，湿度传感器，树莓派cpu温度，有毒气体传感器，红外人体传感器\");\r\n");
      out.write("        }\r\n");
      out.write("    </script>\r\n");
      out.write("    <form action=\"sensor_add_do.html\" method=\"post\" id=\"addsensor\" >\r\n");
      out.write("        <div class=\"form-group\">\r\n");
      out.write("            <label for=\"sensorName\">传感器名</label>\r\n");
      out.write("            <input type=\"text\" class=\"form-control\" name=\"sensorName\" id=\"sensorName\" placeholder=\"请输入传感器名\">\r\n");
      out.write("        </div>\r\n");
      out.write("        <div class=\"form-group\">\r\n");
      out.write("            <label for=\"sensorAddress\">位置</label>\r\n");
      out.write("            <input type=\"text\" class=\"form-control\" name=\"sensorAddress\" id=\"sensorAddress\"  placeholder=\"请输入位置\">\r\n");
      out.write("        </div>\r\n");
      out.write("        <div class=\"form-group\">\r\n");
      out.write("            <label for=\"sensorIntroduction\">简介</label>\r\n");
      out.write("            <textarea class=\"form-control\" rows=\"3\"  name=\"sensorIntroduction\" id=\"sensorIntroduction\" placeholder=\"请输入简介\"></textarea>\r\n");
      out.write("        </div>\r\n");
      out.write("        <div class=\"form-group\">\r\n");
      out.write("            <label for=\"sensorPrice\">价格(单位：元)</label>\r\n");
      out.write("            <input type=\"text\" class=\"form-control\"  name=\"sensorPrice\"  id=\"sensorPrice\" placeholder=\"请输入价格\">\r\n");
      out.write("        </div>\r\n");
      out.write("        <div class=\"form-group\">\r\n");
      out.write("            <label for=\"stateselect\">状态</label>\r\n");
      out.write("                <select class=\"form-control\" id=\"stateselect\" placeholder=\"请输入传感器状态\">\r\n");
      out.write("                    <option value=\"1\" id=\"sensorState1\" style=\"background-color:#10adff\">正常工作</option>\r\n");
      out.write("                    <option value=\"0\" id=\"sensorState2\" style=\"background-color: #ff1a15\">异常</option>\r\n");
      out.write("                </select>\r\n");
      out.write("        </div>\r\n");
      out.write("        <input type=\"submit\" value=\"添加\" class=\"btn btn-success btn-sm\" class=\"text-left\">\r\n");
      out.write("        <script>\r\n");
      out.write("            function mySubmit(flag){\r\n");
      out.write("                return flag;\r\n");
      out.write("            }\r\n");
      out.write("            $(\"#addsensor\").submit(function () {\r\n");
      out.write("                if($(\"#sensorName\").val()==''||$(\"#sensorIntroduction\").val()==''||$(\"#sensorPrice\").val()==''||$(\"#sensorId\").val()==''||$(\"#sensorState\").val()==''){\r\n");
      out.write("                    alert(\"请填入完整传感器信息！\");\r\n");
      out.write("                    return mySubmit(false);\r\n");
      out.write("                }else{\r\n");
      out.write("                    if($(\"#sensorName\").val()=='温度传感器'||$(\"#sensorName\").val()=='湿度传感器'||$(\"#sensorName\").val()=='树莓派cpu温度'||$(\"#sensorName\").val()=='有毒气体传感器'||$(\"#sensorName\").val()=='红外人体传感器'){\r\n");
      out.write("                        return mySubmit(true);\r\n");
      out.write("                    }else{\r\n");
      out.write("                        alert(\"传感器的名字不符合规范，请按照一定规范来命名：温度传感器，湿度传感器，树莓派cpu温度，有毒气体传感器，红外人体传感器\")\r\n");
      out.write("                        return mySubmit(false);\r\n");
      out.write("                    }\r\n");
      out.write("                }\r\n");
      out.write("            })\r\n");
      out.write("        </script>\r\n");
      out.write("    </form>\r\n");
      out.write("</div>\r\n");
      out.write("</body>\r\n");
      out.write("</html>\r\n");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try {
            if (response.isCommitted()) {
              out.flush();
            } else {
              out.clearBuffer();
            }
          } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
