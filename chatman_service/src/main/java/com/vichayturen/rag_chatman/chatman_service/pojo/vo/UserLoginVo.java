package com.vichayturen.rag_chatman.chatman_service.pojo.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserLoginVo {
    private Long id;
    private String email;
    private String username;
    private String password;
    private String createTime;
    private String token;
}
