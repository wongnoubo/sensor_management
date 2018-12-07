package com.sensor.utils;

import com.sensor.dao.AdminDao;
import com.sensor.domain.Admin;
import com.sensor.service.SensorService;
import com.sensor.service.LoginService;

import com.sensor.web.LoginController;
import com.sun.mail.util.MailSSLSocketFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;


import java.util.Random;
import java.util.Date;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

@Component
public class EmailUtils {
        private static Logger logger = Logger.getLogger(EmailUtils.class);
        @Resource
        private LoginService loginService;
        private static EmailUtils emailUtils;

        @PostConstruct
        public void init(){
                emailUtils = this;
                emailUtils.loginService = this.loginService;
        }
    public static void sendMail(String toemail, String emailMsg)
            throws Exception {
            String from = "jiajiasensorsystem@163.com";
            LoginService loginService = new LoginService();
            String subject = "jiajia安全系统用户你好！";//标题
            String body = emailMsg;
            String smtpHost = "smtp.163.com";//smtp.qq.com/smtp.sohu.com
            Properties props = new Properties();
            props.setProperty("mail.transport.protocol", "smtp"); // 使用的协议（JavaMail规范要求）
            props.setProperty("mail.smtp.host", smtpHost); // 发件人的邮箱的 SMTP服务器地址
            props.setProperty("mail.smtp.auth", "true"); // 请求认证，参数名称与具体实现有关
            props.put("mail.smtp.ssl.enable", "true");
            // 创建Session实例对象
            Session session = Session.getDefaultInstance(props);
            // 创建MimeMessage实例对象
            MimeMessage message = new MimeMessage(session);
            // 设置发件人
            message.setFrom(new InternetAddress(from));
            // 设置收件人
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toemail));
            // 设置发送日期
            message.setSentDate(new Date());
            // 设置邮件主题
            message.setSubject(subject);
            // 设置纯文本内容的邮件正文
            message.setText(body);
            // 保存并生成最终的邮件内容
            message.saveChanges();
            // 设置为debug模式, 可以查看详细的发送 log
            session.setDebug(true);
            // 获取Transport对象
            Transport transport = session.getTransport("smtp");
            // 第2个参数需要填写的是QQ邮箱的SMTP的授权码，授权码
            transport.connect(from, "123456jnu");
            // 发送，message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }
     public  static void findPasswordEmail(String email) throws Exception{
             String fromEmail = "jiajiasensorsystem@163.com";
             String adminPassword = emailUtils.loginService.getAdminUser(email).getPassword();
             String subject = "jiajia安全系统找回密码";//标题
             String body = "您的密码是："+adminPassword+",请妥善保管！";
             String smtpHost = "smtp.163.com";//smtp.qq.com/smtp.sohu.com
             Properties props = new Properties();
             props.setProperty("mail.transport.protocol", "smtp"); // 使用的协议（JavaMail规范要求）
             props.setProperty("mail.smtp.host", smtpHost); // 发件人的邮箱的 SMTP服务器地址
             props.setProperty("mail.smtp.auth", "true"); // 请求认证，参数名称与具体实现有关
             props.put("mail.smtp.ssl.enable", "true");
             // 创建Session实例对象
             Session session = Session.getDefaultInstance(props);
             // 创建MimeMessage实例对象
             MimeMessage message = new MimeMessage(session);
             // 设置发件人
             message.setFrom(new InternetAddress(fromEmail));
             // 设置收件人
             message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
             // 设置发送日期
             message.setSentDate(new Date());
             // 设置邮件主题
             message.setSubject(subject);
             // 设置纯文本内容的邮件正文
             message.setText(body);
             // 保存并生成最终的邮件内容
             message.saveChanges();
             // 设置为debug模式, 可以查看详细的发送 log
             session.setDebug(true);
             // 获取Transport对象
             Transport transport = session.getTransport("smtp");
             // 第2个参数需要填写的是QQ邮箱的SMTP的授权码，授权码
             transport.connect(fromEmail, "123456jnu");
             // 发送，message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
             transport.sendMessage(message, message.getAllRecipients());
             transport.close();
     }

     public static void findUsernameEmail(String email) throws Exception{
         String fromEmail = "jiajiasensorsystem@163.com";
         int adminName = emailUtils.loginService.getAdminUser(email).getAdminId();
         String subject = "jiajia安全系统找回用户名";//标题
         String body = "您的用户名是："+adminName+",请妥善保管！";
         String smtpHost = "smtp.163.com";//smtp.qq.com/smtp.sohu.com
         Properties props = new Properties();
         props.setProperty("mail.transport.protocol", "smtp"); // 使用的协议（JavaMail规范要求）
         props.setProperty("mail.smtp.host", smtpHost); // 发件人的邮箱的 SMTP服务器地址
         props.setProperty("mail.smtp.auth", "true"); // 请求认证，参数名称与具体实现有关
         props.put("mail.smtp.ssl.enable", "true");
         // 创建Session实例对象
         Session session = Session.getDefaultInstance(props);
         // 创建MimeMessage实例对象
         MimeMessage message = new MimeMessage(session);
         // 设置发件人
         message.setFrom(new InternetAddress(fromEmail));
         // 设置收件人
         message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
         // 设置发送日期
         message.setSentDate(new Date());
         // 设置邮件主题
         message.setSubject(subject);
         // 设置纯文本内容的邮件正文
         message.setText(body);
         // 保存并生成最终的邮件内容
         message.saveChanges();
         // 设置为debug模式, 可以查看详细的发送 log
         session.setDebug(true);
         // 获取Transport对象
         Transport transport = session.getTransport("smtp");
         // 第2个参数需要填写的是QQ邮箱的SMTP的授权码，授权码
         transport.connect(fromEmail, "123456jnu");
         // 发送，message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
         transport.sendMessage(message, message.getAllRecipients());
         transport.close();
    }

    public static void sendRegisterCode(String email,String code) throws Exception{
        String fromEmail = "jiajiasensorsystem@163.com";
        String subject = "jiajia安全系统注册验证码";//标题
        String body = "这是一封激活邮件,激活请点击以下链接:http://localhost:8080/welcome.html";
        String smtpHost = "smtp.163.com";//smtp.qq.com/smtp.sohu.com
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp"); // 使用的协议（JavaMail规范要求）
        props.setProperty("mail.smtp.host", smtpHost); // 发件人的邮箱的 SMTP服务器地址
        props.setProperty("mail.smtp.auth", "true"); // 请求认证，参数名称与具体实现有关
        props.put("mail.smtp.ssl.enable", "true");
        // 创建Session实例对象
        Session session = Session.getDefaultInstance(props);
        // 创建MimeMessage实例对象
        MimeMessage message = new MimeMessage(session);
        // 设置发件人
        message.setFrom(new InternetAddress(fromEmail));
        // 设置收件人
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
        // 设置发送日期
        message.setSentDate(new Date());
        // 设置邮件主题
        message.setSubject(subject);
        // 设置纯文本内容的邮件正文
        message.setText(body);
        // 保存并生成最终的邮件内容
        message.saveChanges();
        // 设置为debug模式, 可以查看详细的发送 log
        session.setDebug(true);
        // 获取Transport对象
        Transport transport = session.getTransport("smtp");
        // 第2个参数需要填写的是QQ邮箱的SMTP的授权码，授权码
        transport.connect(fromEmail, "123456jnu");
        // 发送，message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }

    public static String generateCode(){
        Random r = new Random();
        StringBuffer captcha1 = new StringBuffer();
        for (int i = 0; i < 4; i++) {
            captcha1.append(r.nextInt(9)+"");
        }
        String captcha = new String(captcha1);
        logger.debug("验证码生成成功");
        return captcha;
    }
}
