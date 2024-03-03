package com.vichayturen.rag_chatman.utils;

import com.vichayturen.rag_chatman.constant.Prompt;

public class PromptUtils {
    public static String ragPrompt(String context, String question) {
        return Prompt.RAG_TMPL
                .replaceFirst("\\{context}", context);
    }
}
