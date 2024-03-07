package com.vichayturen.rag_chatman.web.service.impl;

import com.vichayturen.rag_chatman.constant.RedisKeyConstant;
import com.vichayturen.rag_chatman.exception.BaseException;
import com.vichayturen.rag_chatman.mail.MyMailSender;
import com.vichayturen.rag_chatman.pojo.entity.UserEntity;
import com.vichayturen.rag_chatman.web.mapper.UserMapper;
import com.vichayturen.rag_chatman.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private MyMailSender myMailSender;

    @Override
    public UserEntity login(String username, String password) {
        UserEntity user = userMapper.getUserFromUsername(username);
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(user.getPassword())) {
            throw new BaseException("密码错误！");
        } else {
            return user;
        }
    }

    @Override
    public void signup(String email, String username, String password, String vcode) {
        if (!vcode.equals(redisTemplate.opsForValue().get(RedisKeyConstant.VCODE_PREFIX+email))) {
            throw new BaseException("验证码错误！");
        }
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        UserEntity user = UserEntity.builder()
                .email(email)
                .username(username)
                .password(password)
                .createTime(LocalDateTime.now())
                .build();
        userMapper.addUser(user);
    }

    @Override
    public void vcode(String email) {
        if (!isEmailFormat(email)) {
            throw new BaseException("邮件格式不正确！");
        }
        UserEntity user = userMapper.getUserFromEmail(email);
        if (user != null) {
            throw new BaseException("当前邮箱已注册！");
        }
        String vcode = randomVcode();
        redisTemplate.opsForValue().set(RedisKeyConstant.VCODE_PREFIX+email, vcode);
        myMailSender.sendVcode(email, vcode);
    }

    private boolean isEmailFormat(String email) {
        // TODO: 实现方法
        return true;
    }

    private String randomVcode() {
        // TODO: 前面补0
        return Integer.toString((int) (new Random().nextFloat() * 1000000));
    }
}
