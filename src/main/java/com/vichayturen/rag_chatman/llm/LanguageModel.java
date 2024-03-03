package com.vichayturen.rag_chatman.llm;

import com.vichayturen.rag_chatman.entity.Message;

import java.util.List;

public interface LanguageModel {
    String syncGenerate(String input, List<Message> history);
    void asyncGenerate(String input, List<Message> history);
}
