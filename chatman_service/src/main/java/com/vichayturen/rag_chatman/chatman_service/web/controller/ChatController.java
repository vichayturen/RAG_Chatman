package com.vichayturen.rag_chatman.chatman_service.web.controller;

import com.vichayturen.rag_chatman.chatman_service.pojo.dto.AddChatDto;
import com.vichayturen.rag_chatman.chatman_service.pojo.dto.ChatDto;
import com.vichayturen.rag_chatman.chatman_service.pojo.vo.ChatVo;
import com.vichayturen.rag_chatman.chatman_service.result.Result;
import com.vichayturen.rag_chatman.chatman_service.context.BaseContext;
import com.vichayturen.rag_chatman.chatman_service.web.service.ChatService;
import com.vichayturen.rag_chatman.chatman_service.web.service.LibraryService;
import com.vichayturen.rag_chatman.chatman_service.web.service.SseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@CrossOrigin
@Controller
@RequestMapping("/chat")
@Slf4j
public class ChatController {
    @Autowired
    private SseService sseService;
    @Autowired
    private ChatService chatService;
    @Autowired
    private LibraryService libraryService;

    @PostMapping("/chat")
    @ResponseBody
    public Result<Integer> chat(@RequestBody ChatDto chatDto) {
        log.info("接收到dto：{}", chatDto);
        Long userId = BaseContext.getCurrentId();
        int tokenNum = sseService.sseChat(userId, chatDto);
        return Result.success(tokenNum);
    }

    @GetMapping("/createSse")
    public SseEmitter createSse() {
        Long userId = BaseContext.getCurrentId();
        return sseService.createSse(userId);
    }

    @PostMapping("/addChat")
    @ResponseBody
    public Result<ChatVo> addChat(@RequestBody AddChatDto addChatDto) {
        Long userId = BaseContext.getCurrentId();
        chatService.addChat(userId, addChatDto.getChatName(), addChatDto.getLibraryId());
        return Result.success();
    }

    @PostMapping("/deleteChat")
    @ResponseBody
    public Result deleteChat(Long chatId) {
        Long userId = BaseContext.getCurrentId();
        chatService.deleteChat(userId, chatId);
        return Result.success();
    }

    @PostMapping("/updateChat")
    public Result updateChat(Long chatId, String newName) {
        Long userId = BaseContext.getCurrentId();
        chatService.updateChat(userId, chatId, newName);
        return Result.success();
    }

    @GetMapping("/getAllChat")
    @ResponseBody
    public Result<List<ChatVo>> getAllChat() {
        Long userId = BaseContext.getCurrentId();
        List<ChatVo> chatVoList = chatService.getAllChat(userId);
        return Result.success(chatVoList);
    }

    @GetMapping("/getChatById")
    @ResponseBody
    public Result<ChatVo> getChat(Long chatId) {
        Long userId = BaseContext.getCurrentId();
        ChatVo chatVo = chatService.getChatById(userId, chatId);
        return Result.success(chatVo);
    }
}
