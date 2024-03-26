package com.vichayturen.rag_chatman.chatman_service.pojo.dto;

import lombok.Data;

@Data
public class UserSignupDto {
    private String email;
    private String username;
    private String password;
    private String vcode;
}
