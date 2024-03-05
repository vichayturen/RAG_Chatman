package com.vichayturen.rag_chatman.llm;

import com.vichayturen.rag_chatman.pojo.entity.Message;
import okhttp3.sse.EventSourceListener;

import java.util.List;

public interface LanguageModel {
    void streamChat(String input, List<Message> history, EventSourceListener eventSourceListener);
    String chat(String input, List<Message> history);
}
