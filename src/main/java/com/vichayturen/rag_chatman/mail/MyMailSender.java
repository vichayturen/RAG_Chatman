package com.vichayturen.rag_chatman.mail;

import com.vichayturen.rag_chatman.constant.MailConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class MyMailSender {
    @Autowired
    JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String mailFrom;

    public void sendVcode(String targetMail, String vcode) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(mailFrom);
        msg.setTo(targetMail);
        msg.setSubject(MailConstant.TITLE);
        msg.setText(MailConstant.TEXT + vcode);
        javaMailSender.send(msg);
    }
}
