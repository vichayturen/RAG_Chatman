package com.vichayturen.rag_chatman.chatman_service.llm;

import com.vichayturen.rag_chatman.chatman_service.pojo.entity.Message;
import okhttp3.sse.EventSourceListener;

import java.util.List;

public class ChatGPT implements LanguageModel {
    @Override
    public void streamChat(String input, List<Message> history, EventSourceListener eventSourceListener) {

    }

    @Override
    public String chat(String input, List<Message> history) {
        return null;
    }
}
