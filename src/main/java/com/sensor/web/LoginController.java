package com.sensor.web;

import com.sensor.domain.Admin;
import com.sensor.service.LoginService;
import com.sensor.utils.EmailUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@Controller
public class LoginController {
    private LoginService loginService;
    private static Logger logger = Logger.getLogger(LoginController.class);
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
    public @ResponseBody Object loginCheck(HttpServletRequest request,RedirectAttributes redirectAttributes){
        int id=Integer.parseInt(request.getParameter("id"));
        String passwd = request.getParameter("passwd");
        if(loginService.getAdminStateByAdminId(id)==1) {//判断账号是否激活，没有激活不能登录
            logger.debug("账号已经激活，可以登录");
            boolean isAdmin = loginService.hasMatchAdmin(id, passwd);
            HashMap<String, String> res = new HashMap<String, String>();
            if (isAdmin == false) {
                res.put("stateCode", "0");
                res.put("msg", "账号或密码错误！");
                logger.debug("账号或者密码错误！");
            } else {
                Admin admin = new Admin();
                admin.setAdminId(id);
                admin.setPassword(passwd);
                admin.setNickname(loginService.getAdminById(id).getNickname());
                admin.setEmail(loginService.getAdminById(id).getEmail());
                request.getSession().setAttribute("admin", admin);
                res.put("stateCode", "1");
                res.put("msg", "管理员登陆成功！");
                logger.debug("管理员登录成功");
            }
            return res;
        }else{
            logger.debug("账号未激活，不能登录");
            redirectAttributes.addFlashAttribute("error", "账号没有激活，不能登录！");
            return null;
        }
    }

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

    @RequestMapping("/admin_findpassword.html")
    public ModelAndView findPassword(){
        return new ModelAndView("admin_findpassword");
    }

    //找回密码
    @RequestMapping("/admin_findpassword_do.html")
    public String findPasswordDo(HttpServletRequest request,RedirectAttributes redirectAttributes) {
        String adminEmail = request.getParameter("adminemail");
        logger.debug("jsp传来的邮箱：" + adminEmail);
        if (adminEmail.length() == 0 || adminEmail.equals("") || adminEmail == null) {
            logger.debug("js邮箱校验显示这个邮箱是空的，格式是错误的");
            return "admin_findpassword";
        } else {
            try {
                EmailUtils.findPasswordEmail(adminEmail);
                redirectAttributes.addFlashAttribute("succ", "查找密码的邮件发送成功");
                logger.debug("查找密码的邮件发送成功");
            } catch (Exception e) {
                logger.debug("查找密码邮件发送失败");
                redirectAttributes.addFlashAttribute("error", "查找密码的邮件发送成功");
                e.printStackTrace();
            }
            return "redirect:/login.html";
        }
    }

    @RequestMapping("/admin_findusername.html")
    public ModelAndView findUsername(){
        return new ModelAndView("admin_findusername");
    }

    @RequestMapping("admin_findusername_do.html")
    public String findUsernameDo(HttpServletRequest request,RedirectAttributes redirectAttributes) {
        String adminEmail = request.getParameter("adminemail");
        logger.debug("jsp传来的邮箱：" + adminEmail);
        if (adminEmail.length() == 0 || adminEmail.equals("") || adminEmail == null) {
            logger.debug("js邮箱校验显示这个邮箱是空的，格式是错误的");
            return "admin_findusername";
        } else {
            try {
                EmailUtils.findUsernameEmail(adminEmail);
                logger.debug("查找用户名的邮件发送成功");
                redirectAttributes.addFlashAttribute("succ", "查找用户名的邮件发送成功");
            } catch (Exception e) {
                logger.debug("查找用户名的邮件发送失败");
                redirectAttributes.addFlashAttribute("succ", "查找用户名的邮件发送失败");
                e.printStackTrace();
            }
            return "redirect:/login.html";
        }
    }

    @RequestMapping("/admin_register.html")
    public ModelAndView registerAdmin(){
        return new ModelAndView("admin_register");
    }

    @RequestMapping("/admin_register_do.html")
    public String registerAdminDo(HttpServletRequest httpServletRequest,RedirectAttributes redirectAttributes) {
        int RegiState = 0;
        Admin admin = new Admin();
        String adminEmail = httpServletRequest.getParameter("emailid");
        String adminPassword = httpServletRequest.getParameter("repasswordid");
        String adminNickName = httpServletRequest.getParameter("nickname");
        logger.debug("用户注册邮箱"+adminEmail);
        if (adminEmail.length() == 0 || adminEmail.equals("") || adminEmail == null) {
            logger.debug("用户邮箱输入不正确");
            return "admin_register";
        } else {
            if (loginService.getAdminUser(adminEmail).getAdminId() == 0) {
                logger.debug("该邮箱未被注册");
                String infotablename = "sensorinfo";
                int tableNum = loginService.getElementNumber();
                boolean isCreateSensorManageTable = loginService.createSensorTable(infotablename + tableNum);
                if (isCreateSensorManageTable) {
                    logger.debug("创建管理数据库表成功，数据库表的名字是：" + infotablename + tableNum);
                } else {
                    logger.debug("创建管理数据库表失败。");
                }
                logger.debug("校验验证码");
                String code = EmailUtils.generateCode();
                if (!code.isEmpty()) {
                    logger.debug("产生验证码成功" + code);
                } else {
                    logger.debug("产生验证码失败");
                }
                try {
                    EmailUtils.sendRegisterCode(adminEmail, code);
                    logger.debug("发送注册验证码邮件成功");
                } catch (Exception e) {
                    logger.debug("发送注册验证码邮件失败");
                    e.printStackTrace();
                }
                admin.setEmail(adminEmail);
                admin.setPassword(adminPassword);
                admin.setNickname(adminNickName);
                admin.setCode(code);
                admin.setState(RegiState);
                admin.setInfotablename(infotablename + tableNum);
                boolean isInserUser = loginService.registerAdminUser(admin);
                if (isInserUser) {
                    logger.debug("插入注册用户成功，用户未激活");
                    redirectAttributes.addFlashAttribute("succ", "注册用户成功，请点击邮箱激活链接激活");
                } else {
                    logger.debug("插入注册用户失败");
                    redirectAttributes.addFlashAttribute("error", "注册用户失败，请联系管理员");
                }
                redirectAttributes.addFlashAttribute("用户未激活");
                return "redirect:/login.html";
            } else {
                redirectAttributes.addFlashAttribute("error", "该邮箱已经被注册，请登录");
                logger.debug("该邮箱已经被注册，请登录");
                return "redirect:/login.html";
            }
        }
    }

    @RequestMapping("/welcome")
    public ModelAndView emailRegisterPage(@RequestParam("code") String code,ModelAndView modelAndView){
        return new ModelAndView("welcome","code",code);
    }

    @RequestMapping("/welcome_do.html")
    public String emailRegisterDo(HttpServletRequest httpServletRequest,RedirectAttributes redirectAttributes){
        String code = httpServletRequest.getParameter("Code");
        logger.debug("注册code:"+code);
        if(loginService.checkRegisterCode(code).getPassword().isEmpty()){
            logger.debug("注册码无效");
        }
        else{
            logger.debug("注册码有效");
            boolean isChangeState = loginService.changeAdminState(code,1);
            if(isChangeState){
                logger.debug("用户已经激活");
                int adminId = loginService.checkRegisterCode(code).getAdminId();
                String email = loginService.checkRegisterCode(code).getEmail();
                try {
                    EmailUtils.sendMail(email, "用户已经激活,你的用户ID是："+adminId+"可用于登录系统。【家+安全系统】");
                    redirectAttributes.addFlashAttribute("succ","用户已经激活,你的用户ID是："+adminId+"可用于登录系统。【家+安全系统】");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else {
                logger.debug("用户还未激活");
                redirectAttributes.addFlashAttribute("error","激活用户失败，请联系管理员");
            }
        }
        return "redirect:/login.html";
    }

    @RequestMapping("/admininformation")
    public ModelAndView adminInfomationDo(HttpServletRequest request){
        int adminId = Integer.parseInt(request.getParameter("adminId"));
        Admin admin = loginService.getAdminById(adminId);
        ModelAndView modelAndView = new ModelAndView("admininformation");
        modelAndView.addObject("admininformation",admin);
        return  modelAndView;
    }
}
