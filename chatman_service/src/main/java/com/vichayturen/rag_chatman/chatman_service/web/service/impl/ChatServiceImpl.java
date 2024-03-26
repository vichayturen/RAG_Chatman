package com.vichayturen.rag_chatman.chatman_service.web.service.impl;

import com.vichayturen.rag_chatman.chatman_service.pojo.entity.ChatEntity;
import com.vichayturen.rag_chatman.chatman_service.pojo.vo.ChatVo;
import com.vichayturen.rag_chatman.chatman_service.constant.ChatConstant;
import com.vichayturen.rag_chatman.chatman_service.exception.BaseException;
import com.vichayturen.rag_chatman.chatman_service.web.mapper.ChatMapper;
import com.vichayturen.rag_chatman.chatman_service.web.mapper.LibraryMapper;
import com.vichayturen.rag_chatman.chatman_service.web.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class ChatServiceImpl implements ChatService {
    @Autowired
    private ChatMapper chatMapper;
    @Autowired
    private LibraryMapper libraryMapper;

    @Override
    public void addChat(Long userId, String chatName, Long libraryId) {
        if (chatName.isEmpty()) chatName = ChatConstant.DEFAULT_CHAT_NAME;
        chatMapper.addChat(userId, libraryId, chatName, LocalDateTime.now());
    }

    @Override
    public void deleteChat(Long userId, Long chatId) {
        ChatEntity chat = chatMapper.getChatByChatId(chatId);
        if (!Objects.equals(chat.getUserId(), userId)) {
            throw new BaseException("删除失败！");
        }
        chatMapper.deleteChatById(chatId);
    }

    @Override
    public void updateChat(Long userId, Long chatId, String newName) {
        ChatEntity chat = chatMapper.getChatByChatId(chatId);
        if (!Objects.equals(chat.getUserId(), userId)) {
            throw new BaseException("更新失败！");
        }
        chatMapper.updateChat(chatId, newName);
    }

    @Override
    public List<ChatVo> getAllChat(Long userId) {
        List<ChatVo> chatVoList = chatMapper.getAllChatByUserId(userId);
        return chatVoList;
    }

    @Override
    public ChatVo getChatById(Long userId, Long chatId) {
        return chatMapper.getChatVoById(chatId);
    }
}
