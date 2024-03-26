package com.vichayturen.rag_chatman.chatman_service.mail;

import com.alibaba.fastjson.JSON;
import com.vichayturen.rag_chatman.chatman_service.constant.MailConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@Slf4j
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

    @RabbitListener(queues="chatman-vcode")
    public void sendVcode2(String mailInfoJson) {
        log.info("rabbitmq收到消息，准备发送邮件...");
        MailInfo mailInfo = JSON.parseObject(mailInfoJson, MailInfo.class);
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(mailFrom);
        msg.setTo(mailInfo.getTargetMail());
        msg.setSubject(MailConstant.TITLE);
        msg.setText(MailConstant.TEXT + mailInfo.getVcode());
        javaMailSender.send(msg);
    }
}
