package com.vichayturen.rag_chatman.chatman_service.web.service;

import com.vichayturen.rag_chatman.chatman_service.pojo.entity.UserEntity;

public interface UserService {
    UserEntity login(String username, String password);

    void vcode(String email);

    void signup(String email, String username, String password, String vcode);
}
