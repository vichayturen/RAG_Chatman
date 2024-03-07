package com.vichayturen.rag_chatman.llm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.Serializable;

@Slf4j
public class LlmSseEmitter extends SseEmitter implements Serializable {
    public LlmSseEmitter(Long timeout) {
        super(timeout);
    }
}
