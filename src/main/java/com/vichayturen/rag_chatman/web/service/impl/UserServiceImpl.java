package com.vichayturen.rag_chatman.web.service.impl;

import com.alibaba.fastjson.JSON;
import com.vichayturen.rag_chatman.constant.RedisKeyConstant;
import com.vichayturen.rag_chatman.exception.BaseException;
import com.vichayturen.rag_chatman.mail.MailInfo;
import com.vichayturen.rag_chatman.mail.MyMailSender;
import com.vichayturen.rag_chatman.pojo.entity.UserEntity;
import com.vichayturen.rag_chatman.web.mapper.UserMapper;
import com.vichayturen.rag_chatman.web.service.UserService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private MyMailSender myMailSender;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public UserEntity login(String username, String password) {
        UserEntity user = userMapper.getUserFromUsername(username);
        if (user == null) {
            throw new BaseException("用户名不存在！");
        }
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(user.getPassword())) {
            throw new BaseException("密码错误！");
        } else {
            return user;
        }
    }

    @Override
    public void signup(String email, String username, String password, String vcode) {
        UserEntity user = userMapper.getUserFromEmail(email);
        if (user != null) {
            throw new BaseException("当前邮箱已被注册！");
        }
        UserEntity user2 = userMapper.getUserFromUsername(username);
        if (user2 != null) {
            throw new BaseException("当前用户名已被注册！");
        }
        if (!vcode.equals(redisTemplate.opsForValue().get(RedisKeyConstant.VCODE_PREFIX+email))) {
            throw new BaseException("验证码错误！");
        }
        if (password.length() < 8) {
            throw new BaseException("密码过短，需要至少8位字符！");
        }
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        UserEntity user3 = UserEntity.builder()
                .email(email)
                .username(username)
                .password(password)
                .createTime(LocalDateTime.now())
                .build();
        userMapper.addUser(user3);
        redisTemplate.delete(RedisKeyConstant.VCODE_PREFIX+email);
    }

    @Override
    public void vcode(String email) {
        if (!isEmailFormat(email)) {
            throw new BaseException("邮箱格式不正确！");
        }
        UserEntity user = userMapper.getUserFromEmail(email);
        if (user != null) {
            throw new BaseException("当前邮箱已被注册！");
        }
        String vcode = randomVcode();
        redisTemplate.opsForValue().set(RedisKeyConstant.VCODE_PREFIX+email, vcode, 15, TimeUnit.MINUTES);
        MailInfo mailInfo = new MailInfo();
        mailInfo.setTargetMail(email);
        mailInfo.setVcode(vcode);
        rabbitTemplate.convertAndSend("chatman-vcode", JSON.toJSONString(mailInfo));
    }

    private boolean isEmailFormat(String email) {
        // TODO: 实现方法
        return true;
    }

    private String randomVcode() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int r = (int) (Math.random()*10);
            char numChar = (char) ('0' + r);
            sb.append(numChar);
        }
        return sb.toString();
    }
}
