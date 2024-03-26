package com.vichayturen.rag_chatman.chatman_service.utils;

import com.vichayturen.rag_chatman.chatman_service.constant.PromptConstant;

public class PromptUtils {
    public static String ragPrompt(String context, String question) {
        return PromptConstant.RAG_TMPL
                .replaceAll("\\{context}", context)
                .replaceAll("\\{question}", question);
    }
}
