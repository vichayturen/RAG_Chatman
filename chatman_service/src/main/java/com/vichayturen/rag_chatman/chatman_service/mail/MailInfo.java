package com.vichayturen.rag_chatman.chatman_service.mail;

import lombok.Data;

import java.io.Serializable;

@Data
public class MailInfo implements Serializable {
    private String targetMail;
    private String vcode;
}
