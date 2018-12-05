package com.sensor.utils;

import com.sensor.domain.Admin;
import com.sensor.service.SensorService;
import com.sensor.service.LoginService;

import com.sun.mail.util.MailSSLSocketFactory;
import java.util.Date;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
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

public class EmailUtils {
    public static void sendMail(String toemail, String emailMsg)
            throws Exception {
            String from = "jiajiasensorsystem@163.com";
            LoginService loginService = new LoginService();
           // System.out.println(loginService.getAdminUser(toemail).getAdminId());
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
}
