package com.ucar.train.common.util;

import com.sun.mail.util.MailSSLSocketFactory;
import lombok.extern.slf4j.Slf4j;

import javax.mail.Authenticator;
import javax.mail.Message.RecipientType;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @Classname MailUtils
 * @Description TODO
 * @Date 2019/7/25 9:12
 * @Created by LiuHao
 */
@Slf4j
public class MailUtils {
    /**
     * 发送邮件工具类:通过qq邮件发送,因为具有ssl加密,采用的是smtp协议
     *
     * @param mailServer       邮件服务器的主机名
     * @param loginAccount     登录邮箱的账号
     * @param loginAuthCode    登录qq邮箱时候需要的授权码:
     * @param sender           发件人
     * @param recipients       收件人.支持群发
     * @param emailSubject     邮件的主题
     * @param emailContent     邮件的内容
     * @param emailContentType 邮件内容的类型,支持纯文本:"text/plain;charset=utf-8";,带有Html格式的内容:"text/html;charset=utf-8"
     * @return
     */
    private static void send(String mailServer, final String loginAccount, final String loginAuthCode, String sender, String[] recipients,
                             String emailSubject, String emailContent, String emailContentType) {
        try {
            //跟smtp服务器建立一个连接
            Properties p = new Properties();
            //设置邮件服务器主机名
            p.setProperty("mail.host", mailServer);
            //发送服务器需要身份验证,要采用指定用户名密码的方式去认证
            p.setProperty("mail.smtp.auth", "true");
            //发送邮件协议名称
            p.setProperty("mail.transport.protocol", "smtp");

            //开启SSL加密，否则会失败
            MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            p.put("mail.smtp.ssl.enable", "true");
            p.put("mail.smtp.ssl.socketFactory", sf);
            // 创建session
            Session session = Session.getDefaultInstance(p, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    //用户名可以用QQ账号也可以用邮箱的别名:第一个参数为邮箱账号,第二个为授权码
                    PasswordAuthentication pa = new PasswordAuthentication(loginAccount, loginAuthCode);
                    return pa;
                }
            });

            //设置打开调试状态
            session.setDebug(true);

            //可以发送几封邮件:可以在这里 for循环多次
            //声明一个Message对象(代表一封邮件),从session中创建
            MimeMessage msg = new MimeMessage(session);
            //邮件信息封装
            //1发件人
            msg.setFrom(new InternetAddress(sender));

            //2收件人:可以多个
            //一个的收件人
            InternetAddress[] receptientsEmail = new InternetAddress[recipients.length];
            for (int i = 0; i < recipients.length; i++) {
                receptientsEmail[i] = new InternetAddress(recipients[i]);
            }
            //多个收件人
            msg.setRecipients(RecipientType.TO, receptientsEmail);

            //3邮件内容:主题、内容
            msg.setSubject(emailSubject);
            //msg.setContent("Hello, 我是debug!!!", );//纯文本
            msg.setContent(emailContent, emailContentType);//发html格式的文本
            //发送动作
            Transport.send(msg);
//            System.out.println("邮件发送成功");

        } catch (Exception e) {
            log.debug("邮件发送失败");
        }
        log.debug("邮件发送成功");
    }

    private static void assembleMail(String[] receivers, String mailSubject, String mailContent) {
        final String loginAccount = "894297191@qq.com";//发送人邮件账号
        final String mailServer = "smtp.qq.com";//邮件服务类型
        final String loginAuthCode = "ldzxdjtaexggbcga";//授权码
        final String sender = "894297191@qq.com";//发送人名称
        final String mailContestType = "text/html;charset=utf-8";//邮件内容类型

        send(mailServer, loginAccount, loginAuthCode, sender, receivers,
                mailSubject, mailContent, mailContestType);
    }

    /**
     * 请不要使用此方法
     * @param receiverList 接收人邮件地址
     * @param orderCode    订单号
     * @param orderStatus  订单状态
     */
    public static void orderStatusMail(List<String> receiverList, String orderCode, Object orderStatus) {
        String code = (String) orderCode;
        String status = (String) orderStatus;
        final String subject = "您的订单有状态变更";
        final String content = "尊敬的用户您好，您的订单: [" + code + "]," + "成功" + status + ",感谢您的使用";

        String[] receivers = new String[receiverList.size()];
        receiverList.toArray(receivers);

        assembleMail(receivers, subject, content);
    }

    /**
     * 请不要使用此方法
     * @param receiverList 接收人邮件地址
     * @param userAccount  接收人账户
     * @param oldCredit    变更前的积分
     * @param newCredit    变更后的积分
     */
    public static void creditMail(List<String> receiverList, String userAccount, int oldCredit, int newCredit) {
        String account = userAccount;
        String old = Integer.toString(oldCredit);
        String now = Integer.toString(newCredit);
        String subject = "您的账户有积分变更";
        final String content = "尊敬的用户您好，您的账户:" + account + "，现有积分为:" + now + "，感谢您的使用";
        String[] receivers = new String[receiverList.size()];
        receiverList.toArray(receivers);
        assembleMail(receivers, subject, content);
    }

    /**
     * 请不要使用此方法
     * @param receiverList
     * @param mailSubject
     * @param mailContent
     */
    public static void moduleMail(List<String> receiverList, String mailSubject, String mailContent) {
        String subject = mailSubject;
        String content = mailContent;
        String[] receivers = new String[receiverList.size()];
        receiverList.toArray(receivers);
        assembleMail(receivers, subject, content);
    }

    /**
     * @param receiverList 收件人列表  添加多个联系人可群发邮件
     * @param subject      邮件主题
     * @param content      邮件内容
     * @return
     */
    public static void sendMail(List<String> receiverList, String subject, String content) {
        String[] receivers = new String[receiverList.size()];
        receiverList.toArray(receivers);
        assembleMail(receivers,subject,content);
    }

    private static void demo() {
        String receiver1 = "xxxxxxxx@163.com";//接收人邮箱
        String receiver2 = "xxxxxxxx@163.com";//接收人邮箱

        List<String> recList = new ArrayList<>();
        //添加收件人
        recList.add(receiver1);
        recList.add(receiver2);

        sendMail(recList, "邮件主题", "邮件内容");
    }
}
