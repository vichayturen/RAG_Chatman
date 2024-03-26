package com.vichayturen.rag_chatman.chatman_service.pojo.entity;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
public class UserEntity implements Serializable {
    private Long id;
    private String email;
    private String username;
    private String password;
    private LocalDateTime createTime;
}
