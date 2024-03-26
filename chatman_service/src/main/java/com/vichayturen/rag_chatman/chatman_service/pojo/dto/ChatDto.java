package com.vichayturen.rag_chatman.chatman_service.pojo.dto;

import com.vichayturen.rag_chatman.chatman_service.pojo.entity.Message;
import lombok.Data;

import java.util.List;

@Data
public class ChatDto {
    private Long chatId;
    private List<Message> history;
    private String input;
}
