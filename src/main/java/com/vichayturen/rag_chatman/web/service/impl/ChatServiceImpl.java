package com.vichayturen.rag_chatman.web.service.impl;

import com.vichayturen.rag_chatman.constant.ChatConstant;
import com.vichayturen.rag_chatman.exception.BaseException;
import com.vichayturen.rag_chatman.pojo.entity.ChatEntity;
import com.vichayturen.rag_chatman.pojo.entity.LibraryEntity;
import com.vichayturen.rag_chatman.pojo.vo.ChatVo;
import com.vichayturen.rag_chatman.web.mapper.ChatMapper;
import com.vichayturen.rag_chatman.web.mapper.LibraryMapper;
import com.vichayturen.rag_chatman.web.service.ChatService;
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
    public ChatVo addChat(Long userId, Long libraryId) {
        chatMapper.addChat(userId, libraryId, ChatConstant.DEFAULT_CHAT_NAME, LocalDateTime.now());
        LibraryEntity library = libraryMapper.getLibraryById(libraryId);
        ChatVo chatVo = ChatVo.builder()
                .id()
                .name()
                .libraryId()
                .libraryName()
                .build();
        return chatVo;
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
}
