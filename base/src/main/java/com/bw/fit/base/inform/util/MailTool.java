package com.bw.fit.base.inform.util;

import java.security.GeneralSecurityException;
import java.util.Properties;
import java.util.Properties;
import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import com.alibaba.fastjson.*;

import com.sun.mail.util.MailSSLSocketFactory;

/**
 * @Description 邮件工具类
 * @Author yangh
 * @Date 2019-2-25 16:43
 * @Param ${PARAM}
 * @Return ${RETURN}
 * @VERSION
 */

public class MailTool {
    //收件人地址
    public static String recipientAddress = "xxx@163.com";
    //收件人账户名
    public static String recipientAccount = "xxx";
    //收件人账户密码
    public static String recipientPassword = "xxx";

    public static JSONObject send(String subject, StringBuilder content,
                                  InternetAddress[] recievers) {
        JSONObject json = new JSONObject();
        json.put("res", "1");
        try {
            Properties props = new Properties();
            // 开启debug调试
            props.setProperty("mail.debug", "false");
            // 发送服务器需要身份验证
            props.setProperty("mail.smtp.auth", "true");
            // 设置邮件服务器主机名
            props.setProperty("mail.host", "smtp.qq.com");
            // 发送邮件协议名称
            props.setProperty("mail.transport.protocol", "smtp");
            MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            props.put("mail.smtp.ssl.enable", "true");
            props.put("mail.smtp.ssl.socketFactory", sf);
            Session session = Session.getInstance(props);
            Message msg = new MimeMessage(session);
            msg.setSubject(subject);
            msg.setText(content.toString());
            msg.setFrom(new InternetAddress("1027696585@qq.com"));
            Transport transport = session.getTransport();
            transport.connect("smtp.qq.com","1027696585@qq.com","123123");

            transport.sendMessage(msg, recievers);
            transport.close();
            json = new JSONObject();
            json.put("res", "2");
            json.put("msg", "发送成功");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            json.put("msg", e.getMessage());
        } finally {
            return json;
        }
    }

    public static JSONObject receice() throws Exception  {
        //1、连接邮件服务器的参数配置
        Properties props = new Properties();
        //设置传输协议
        props.setProperty("mail.store.protocol", "pop3");
        //设置收件人的POP3服务器
        props.setProperty("mail.pop3.host", "pop3.163.com");
        //2、创建定义整个应用程序所需的环境信息的 Session 对象
        Session session = Session.getInstance(props);
        //设置调试信息在控制台打印出来
        //session.setDebug(true);

        Store store = session.getStore("pop3");
        //连接收件人POP3服务器
        store.connect("pop3.163.com", recipientAccount, recipientPassword);
        //获得用户的邮件账户，注意通过pop3协议获取某个邮件夹的名称只能为inbox
        Folder folder = store.getFolder("inbox");
        //设置对邮件账户的访问权限
        folder.open(Folder.READ_WRITE);

        //得到邮件账户的所有邮件信息
        Message [] messages = folder.getMessages();
        for(int i = 0 ; i < messages.length ; i++){
            //获得邮件主题
            String subject = messages[i].getSubject();
            //获得邮件发件人
            Address[] from = messages[i].getFrom();
            //获取邮件内容（包含邮件内容的html代码）
            String content = (String) messages[i].getContent();
        }

        //关闭邮件夹对象
        folder.close();
        //关闭连接对象
        store.close();
        return null;
    }


}

