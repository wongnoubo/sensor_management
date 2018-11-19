package com.sensor.web;

import com.sensor.domain.Admin;
import com.sensor.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@Controller
public class LoginController {

    private LoginService loginService;

    @Autowired
    public void setLoginService(LoginService loginService){
        this.loginService = loginService;
    }

    //负责处理login.html请求
    @RequestMapping(value = {"/","/login.html"})
    public String toLogin(HttpServletRequest request){
        request.getSession().invalidate();
        return "index";
    }

    @RequestMapping("/logout.html")
    public String logout(HttpServletRequest request){
        request.getSession().invalidate();
        return "redirect:/login.html";
    }

    //负责处理loginCheck.html请求
    //请求参数会根据参数名称默认契约自动绑定到相应方法的入参中
    @RequestMapping(value = "/api/loginCheck", method = RequestMethod.POST)
    public @ResponseBody Object loginCheck(HttpServletRequest request){
        int id=Integer.parseInt(request.getParameter("id"));
        String passwd = request.getParameter("passwd");
        boolean isAdmin = loginService.hasMatchAdmin(id, passwd);
        HashMap<String, String> res = new HashMap<String, String>();
        if (isAdmin==false) {
            res.put("stateCode", "0");
            res.put("msg","账号或密码错误！");
        } else {
            Admin admin=new Admin();
            admin.setAdminId(id);
            admin.setPassword(passwd);
            request.getSession().setAttribute("admin",admin);
            res.put("stateCode", "1");
            res.put("msg","管理员登陆成功！");
        }
        return res;
    };

    @RequestMapping("/admin_main.html")
    public ModelAndView toAdminMain(HttpServletResponse response) {

        return new ModelAndView("admin_main");
    }

    @RequestMapping("/admin_repasswd.html")
    public ModelAndView reAdminPasswd() {

        return new ModelAndView("admin_repasswd");
    }

    @RequestMapping("/admin_repasswd_do")
    public String reAdminPasswdDo(HttpServletRequest request,String oldPasswd,String newPasswd,String reNewPasswd,RedirectAttributes redirectAttributes ) {

        Admin admin=(Admin) request.getSession().getAttribute("admin");
        int id=admin.getAdminId();
        String passwd = loginService.getAdminPassword(id);

        if(passwd.equals(oldPasswd)){
            boolean succ=loginService.adminRePassword(id,newPasswd);
            if (succ){

                redirectAttributes.addFlashAttribute("succ", "密码修改成功！");
                return "redirect:/admin_repasswd.html";
            }
            else {
                redirectAttributes.addFlashAttribute("error", "密码修改失败！");
                return "redirect:/admin_repasswd.html";
            }
        }else {
            redirectAttributes.addFlashAttribute("error", "旧密码错误！");
            return "redirect:/admin_repasswd.html";
        }
    };

    //配置404页面
    @RequestMapping("*")
    public String notFind(){
        return "404";
    }

}
