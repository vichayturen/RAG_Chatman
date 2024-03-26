package com.vichayturen.rag_chatman.chatman_service.web.controller;

import com.vichayturen.rag_chatman.chatman_service.pojo.dto.UserLoginDto;
import com.vichayturen.rag_chatman.chatman_service.pojo.dto.UserSignupDto;
import com.vichayturen.rag_chatman.chatman_service.pojo.dto.VcodeDto;
import com.vichayturen.rag_chatman.chatman_service.pojo.entity.UserEntity;
import com.vichayturen.rag_chatman.chatman_service.pojo.vo.UserLoginVo;
import com.vichayturen.rag_chatman.chatman_service.result.Result;
import com.vichayturen.rag_chatman.chatman_service.utils.JwtUtil;
import com.vichayturen.rag_chatman.chatman_service.constant.JwtClaimsConstant;
import com.vichayturen.rag_chatman.chatman_service.properties.JwtProperties;
import com.vichayturen.rag_chatman.chatman_service.web.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result<UserLoginVo> login(@RequestBody UserLoginDto userLoginDto) {
        String username = userLoginDto.getUsername();
        String password = userLoginDto.getPassword();
        log.info("用户登录：{}；{}；", username, password);
        UserEntity user = userService.login(username, password);
        // 登陆成功
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, user.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getUserSecretKey(),
                jwtProperties.getUserTtl(),
                claims);
        UserLoginVo userLoginVo = UserLoginVo.builder()
                .token(token)
                .build();
        return Result.success(userLoginVo);
    }

    @PostMapping("/vcode")
    public Result vcode(@RequestBody VcodeDto vcodeDto) {
        String email = vcodeDto.getEmail();
        userService.vcode(email);
        return Result.success();
    }

    @PostMapping("/signup")
    public Result signup(@RequestBody UserSignupDto userSignupDto) {
        String username = userSignupDto.getUsername();
        String password = userSignupDto.getPassword();
        String email = userSignupDto.getEmail();
        String vcode = userSignupDto.getVcode();
        userService.signup(email, username, password, vcode);
        return Result.success();
    }
}
