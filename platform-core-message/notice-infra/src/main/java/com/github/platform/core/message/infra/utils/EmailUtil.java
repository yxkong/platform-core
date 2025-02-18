package com.github.platform.core.message.infra.utils;

import com.github.platform.core.common.constant.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;

/**
 * 发送邮件工具类
 * @Author: yxkong
 * @Date: 2025/1/16
 * @version: 1.0
 */
@Slf4j
public class EmailUtil {
    /**
     * 发送邮件
     *
     * @param host       SMTP服务器地址
     * @param port       SMTP服务器端口（25、465、587等）
     * @param username   登录用户名（通常是发件人邮箱地址）
     * @param password   登录密码（或授权码）
     * @param to         收件人列表
     * @param cc         抄送列表
     * @param bcc        密送列表
     * @param subject    邮件主题
     * @param content    邮件内容
     * @param isHtml     是否HTML格式
     * @return 是否发送成功
     */
    public static boolean sendEmail(String host, int port, String username, String password,
                                    List<String> to, List<String> cc, List<String> bcc,
                                    String subject, String content, boolean isHtml) {
        try {
            // 配置邮件客户端
            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
            mailSender.setHost(host);
            mailSender.setPort(port);
            mailSender.setUsername(username);
            mailSender.setPassword(password);

            // 设置邮件属性
            Properties props = mailSender.getJavaMailProperties();
            props.put("mail.smtp.auth", "true");
            configureProtocol(props, port);

            // 构建邮件消息
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, Constants.UTF8);

            // 设置收件人
            helper.setTo(to.toArray(new String[0]));
            // 设置抄送
            if (cc != null && !cc.isEmpty()) {
                helper.setCc(cc.toArray(new String[0]));
            }
            // 设置密送
            if (bcc != null && !bcc.isEmpty()) {
                helper.setBcc(bcc.toArray(new String[0]));
            }
            // 设置邮件主题和内容
            helper.setSubject(subject);
            helper.setText(content, isHtml);
            // 设置发件人
            helper.setFrom(username);

            // 发送邮件
            mailSender.send(mimeMessage);
            log.info("邮件发送成功，主题: {}", subject);
            return true;
        } catch (Exception e) {
            log.error("邮件发送失败，主题：{} 原因: {}",subject, e.getMessage(), e);
            return false;
        }
    }

    /**
     * 根据端口配置协议
     *
     * @param props 邮件属性
     * @param port  SMTP端口
     */
    private static void configureProtocol(Properties props, int port) {
        switch (port) {
            case 465:
                props.put("mail.smtp.ssl.enable", "true");
                props.put("mail.smtp.socketFactory.port", "465");
                props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                break;
            case 587:
                props.put("mail.smtp.starttls.enable", "true");
                break;
            case 25:
            default:
                props.put("mail.smtp.starttls.enable", "false");
                break;
        }
    }
}
