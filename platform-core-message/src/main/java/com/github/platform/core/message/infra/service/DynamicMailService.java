package com.github.platform.core.message.infra.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.Map;
import java.util.Properties;

/**
 * 动态邮件服务
 * @Author: yxkong
 * @Date: 2024/10/10
 * @version: 1.0
 */
@Service
@Slf4j
public class DynamicMailService {

    /**
     * 动态创建JavaMailSender
     * @param host 邮件服务器地址
     * @param port 邮件服务器端口
     * @param username 发件邮箱
     * @param password 邮箱密码
     * @return JavaMailSender 实例
     */
    private JavaMailSenderImpl createMailSender(String host, int port, String username, String password) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.starttls.required", "true");

        return mailSender;
    }

    /**
     * 发送邮件
     * @param host 邮件服务器地址
     * @param port 邮件服务器端口
     * @param username 发件邮箱
     * @param password 邮箱密码
     * @param to 收件人
     * @param subject 主题
     * @param content 邮件内容
     * @param model 模板数据
     * @throws javax.mail.MessagingException
     */
    public void sendEmail(String host, int port, String username, String password,
                          String to, String subject, String content, Map<String, Object> model) throws Exception {

        // 动态创建邮件发送器
        JavaMailSenderImpl mailSender = createMailSender(host, port, username, password);
        // 创建MimeMessage对象
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        // 设置邮件内容
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setFrom(username);
        // 生成邮件内容
        // true 表示发送HTML格式的邮件
        helper.setText(content, true);
        // 发送邮件
        mailSender.send(message);
    }
}
