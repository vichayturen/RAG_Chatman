package com.vichayturen.rag_chatman.pojo.dto;

import com.vichayturen.rag_chatman.pojo.entity.Message;
import lombok.Data;

import java.util.List;

@Data
public class ChatDto {
    private Long chatId;
    private List<Message> history;
    private String input;
}
