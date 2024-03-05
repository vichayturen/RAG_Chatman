package com.vichayturen.rag_chatman.pojo.entity;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class UserEntity implements Serializable {
    private Long id;
    private String email;
    private String username;
    private String password;
    private String createTime;
}
