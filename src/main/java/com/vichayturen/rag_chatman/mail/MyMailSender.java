package com.vichayturen.rag_chatman.mail;

import com.vichayturen.rag_chatman.constant.Mail;
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

    private void sendVarificationCode(String targetMail) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(mailFrom);
        msg.setTo(targetMail);
        msg.setSubject(Mail.TITLE);
        msg.setText(Mail.TEXT + generateVarificationCode());
        javaMailSender.send(msg);
    }

    private String generateVarificationCode() {
        return Integer.toString((int) ((Math.random() * 9 + 1) * 100000));
    }
}
