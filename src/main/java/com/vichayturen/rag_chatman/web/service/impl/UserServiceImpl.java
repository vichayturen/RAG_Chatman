package com.vichayturen.rag_chatman.web.service.impl;

import com.vichayturen.rag_chatman.pojo.entity.UserEntity;
import com.vichayturen.rag_chatman.web.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public UserEntity login(String username, String password) {
        return UserEntity.builder().build();
    }
}
