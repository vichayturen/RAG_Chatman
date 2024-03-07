package com.vichayturen.rag_chatman.utils;

import com.vichayturen.rag_chatman.constant.PromptConstant;

public class PromptUtils {
    public static String ragPrompt(String context, String question) {
        return PromptConstant.RAG_TMPL
                .replaceFirst("\\{context}", context);
    }
}
