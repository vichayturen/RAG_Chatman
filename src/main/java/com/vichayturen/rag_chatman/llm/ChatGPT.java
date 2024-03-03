package com.vichayturen.rag_chatman.llm;

import com.vichayturen.rag_chatman.entity.Message;

import java.util.List;

public class ChatGPT implements LanguageModel {
    @Override
    public String syncGenerate(String input, List<Message> history) {
        return null;
    }

    @Override
    public void asyncGenerate(String input, List<Message> history) {

    }
}
