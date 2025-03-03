package com.example;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class NotificationService {

    public static void sendEmail(String to,double Tem,String weather) throws MessagingException {
        // プロパティファイルから設定を読み込む
        String host = ConfigLoader.getMailProperty("mail.smtp.host");
        String port = ConfigLoader.getMailProperty("mail.smtp.port");
        String from = ConfigLoader.getMailProperty("mail.from");
        String password = ConfigLoader.getMailProperty("mail.password");

        // SMTP設定
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", ConfigLoader.getMailProperty("mail.smtp.auth"));
        properties.put("mail.smtp.starttls.enable", ConfigLoader.getMailProperty("mail.smtp.starttls.enable"));

        // 認証付きセッション作成
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });
        // 現在のタイムスタンプを取得
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH時mm分");
        String timestamp = now.format(formatter);

        // メール本文
        String subject1 = "赤外線センサー通知";
        String body1 = "赤外線センサーが " + timestamp + " に反応しました！"+"¥n天気:"+weather+"¥n気温:"+Tem+"℃";

        // メールメッセージの作成
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject1);
        message.setText(body1);

        // メールの送信
        Transport.send(message);    }
}
