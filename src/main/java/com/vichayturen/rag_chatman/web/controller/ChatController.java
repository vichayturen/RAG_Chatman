package com.vichayturen.rag_chatman.web.controller;

import com.vichayturen.rag_chatman.constant.JwtClaimsConstant;
import com.vichayturen.rag_chatman.context.BaseContext;
import com.vichayturen.rag_chatman.pojo.dto.ChatDto;
import com.vichayturen.rag_chatman.pojo.vo.ChatVo;
import com.vichayturen.rag_chatman.properties.JwtProperties;
import com.vichayturen.rag_chatman.result.Result;
import com.vichayturen.rag_chatman.utils.JwtUtil;
import com.vichayturen.rag_chatman.web.service.ChatService;
import com.vichayturen.rag_chatman.web.service.SseService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.Map;

@CrossOrigin
@Controller
@RequestMapping("/chat")
public class ChatController {
    @Autowired
    private SseService sseService;
    @Autowired
    private ChatService chatService;
    @Autowired
    private JwtProperties jwtProperties;

    @PostMapping("/chat")
    @ResponseBody
    public Result chat(@RequestBody ChatDto chatDto, @RequestHeader Map<String, String> headers) {
        String token = headers.get(jwtProperties.getUserTokenName());
        Claims claims = JwtUtil.parseJWT(jwtProperties.getUserSecretKey(), token);
        Long userId = Long.valueOf(claims.get(JwtClaimsConstant.USER_ID).toString());
        sseService.sseChat(userId, chatDto);
        return Result.success();
    }

    @GetMapping("/createSse")
    public SseEmitter createConnect(@RequestHeader Map<String, String> headers) {
        String token = headers.get(jwtProperties.getUserTokenName());
        Claims claims = JwtUtil.parseJWT(jwtProperties.getUserSecretKey(), token);
        Long userId = Long.valueOf(claims.get(JwtClaimsConstant.USER_ID).toString());
        return sseService.createSse(userId);
    }

    @GetMapping("/getAllChat")
    @ResponseBody
    public Result<List<ChatVo>> getAllChat(Long userId) {
        return Result.success();
    }

    @PostMapping("/newChat")
    @ResponseBody
    public Result<Long> newChat(Long userId) {
        return Result.success();
    }

    @PostMapping("/deleteChat")
    @ResponseBody
    public Result deleteChat(Long userId) {
        return Result.success();
    }
}
