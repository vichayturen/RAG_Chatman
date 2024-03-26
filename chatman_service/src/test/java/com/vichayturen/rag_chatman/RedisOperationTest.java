package com.vichayturen.rag_chatman;

import com.vichayturen.rag_chatman.chatman_service.llm.LlmSseEmitter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@SpringBootTest
public class RedisOperationTest {
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    void test1() {
        redisTemplate.opsForValue().set("123", "yichen");
    }

    @Test
    void test2() {
        SseEmitter sseEmitter = new LlmSseEmitter(0L);
        redisTemplate.opsForValue().set("test1", sseEmitter);
        sseEmitter.onCompletion(() -> {
            System.out.println("hello");
        });
        redisTemplate.opsForValue().set("test2", sseEmitter);
    }
}
