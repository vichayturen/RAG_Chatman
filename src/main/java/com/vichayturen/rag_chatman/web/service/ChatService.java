package com.vichayturen.rag_chatman.web.service;

import com.vichayturen.rag_chatman.pojo.vo.ChatVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ChatService {
    void addChat(Long userId, String chatName, Long libraryId);
    void deleteChat(Long userId, Long chatId);
    void updateChat(Long userId, Long chatId, String newName);
    List<ChatVo> getAllChat(Long userId);
}
