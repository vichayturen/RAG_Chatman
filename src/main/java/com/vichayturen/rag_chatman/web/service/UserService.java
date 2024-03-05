package com.vichayturen.rag_chatman.web.service;

import com.vichayturen.rag_chatman.pojo.entity.UserEntity;

public interface UserService {
    UserEntity login(String username, String password);
}
