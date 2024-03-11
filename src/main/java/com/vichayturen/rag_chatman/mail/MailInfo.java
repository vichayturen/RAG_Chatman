package com.vichayturen.rag_chatman.mail;

import lombok.Data;

import java.io.Serializable;

@Data
public class MailInfo implements Serializable {
    private String targetMail;
    private String vcode;
}
