package com.sensor.interceptor;

import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

import com.sensor.domain.Admin;
/**
 * 登录认证拦截器
 */
public class Logininterceptor {

    private static Logger logger = Logger.getLogger(Logininterceptor.class);
    /**
     * Handler执行完成之后调用这个方法
     */
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exc)
            throws Exception {
    }

    /**
     * Handler执行之后，ModelAndView返回之前调用这个方法
     */
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
    }

    /**
     * Handler执行之前调用这个方法
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 获取Session
        HttpSession session = request.getSession();
        Admin admin = (Admin) session.getAttribute("admin");
        logger.debug("admin==="+admin);
        // 1. 页面不存在 跳转404.html
        // 2. 页面存在 未登录 跳转index.jsp
        // 3. 页面存在 已登录 跳转请求页面

        // 符合条件
        if (admin != null) {
            logger.debug("符合条件。不拦截");
            return true;
        }
        // 不符合条件
        else {
            // 跳转根目录
            String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
            response.sendRedirect(baseUrl);
            logger.debug("不符合条件，拦截");
            return false;
        }
    }
}
