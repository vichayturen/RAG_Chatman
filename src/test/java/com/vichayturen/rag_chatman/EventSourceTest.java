package com.vichayturen.rag_chatman;

import com.vichayturen.rag_chatman.llm.ConsoleEventSourceListener;
import com.vichayturen.rag_chatman.llm.LlmSseEmitter;
import com.vichayturen.rag_chatman.llm.LocalLlm;
import okhttp3.sse.EventSourceListener;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.Serializable;
import java.util.ArrayList;

@SpringBootTest
public class EventSourceTest {
    @Autowired
    LocalLlm localLlm;

    @Test
    void test1() {
        EventSourceListener eventSourceListener = new ConsoleEventSourceListener();
        localLlm.streamChat("你好呀", new ArrayList<>(), eventSourceListener);
    }

    @Test
    void test2() {
        SseEmitter sseEmitter = new SseEmitter(0L);
        System.out.println(sseEmitter instanceof Serializable);
        SseEmitter sseEmitter2 = new LlmSseEmitter(0L);
        System.out.println(sseEmitter2 instanceof Serializable);
    }
}
