package com.vichayturen.rag_chatman.web.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.vichayturen.rag_chatman.exception.BaseException;
import com.vichayturen.rag_chatman.pojo.dto.ChatDto;
import com.vichayturen.rag_chatman.llm.ChatGLM;
import com.vichayturen.rag_chatman.llm.OpenAISSEEventSourceListener;
import com.vichayturen.rag_chatman.pojo.entity.Message;
import com.vichayturen.rag_chatman.web.mapper.ChatMapper;
import com.vichayturen.rag_chatman.web.service.SseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 描述：
 *
 * @author https:www.unfbx.com
 * @date 2023-04-08
 */
@Service
@Slf4j
public class SseServiceImpl implements SseService {

    @Autowired
    private ChatGLM chatGLM;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public SseEmitter createSse(Long userId) {
        //默认30秒超时,设置为0L则永不超时
        SseEmitter sseEmitter = new SseEmitter(0l);
        //完成后回调
        sseEmitter.onCompletion(() -> {
            log.info("[{}]结束连接...................", userId);
            if (redisTemplate.hasKey(userId)) {
                redisTemplate.delete(userId);
            }
        });
        //超时回调
        sseEmitter.onTimeout(() -> {
            log.info("[{}]连接超时...................", userId);
        });
        //异常回调
        sseEmitter.onError(
                throwable -> {
                    try {
                        log.info("[{}]连接异常,{}", userId, throwable.toString());
                        sseEmitter.send(SseEmitter.event()
                                .id(Long.toString(userId))
                                .name("发生异常！")
                                .data(Message.builder().content("发生异常请重试！").build())
                                .reconnectTime(3000));
                        redisTemplate.opsForValue().set(userId, sseEmitter);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        );
        try {
            sseEmitter.send(SseEmitter.event().reconnectTime(5000));
        } catch (IOException e) {
            e.printStackTrace();
        }
        redisTemplate.opsForValue().set(userId, sseEmitter);
        log.info("[{}]创建sse连接成功！", userId);
        return sseEmitter;
    }

    @Override
    public void closeSse(Long userId) {
        SseEmitter sse = (SseEmitter) redisTemplate.opsForValue().get(userId);
        if (sse != null) {
            sse.complete();
            //移除
            if (redisTemplate.hasKey(userId)) {
                redisTemplate.delete(userId);
            }
        }
    }

    @Override
    public void sseChat(Long userId, ChatDto chatDto) {
        if (StrUtil.isBlank(chatDto.getInput())) {
            log.info("参数异常，msg为null", chatDto.getChatId());
            throw new RuntimeException("参数异常，msg不能为空~");
        }
//        SseEmitter sseEmitter = (SseEmitter) LocalCache.CACHE.get(uid);
        SseEmitter sseEmitter = (SseEmitter) redisTemplate.opsForValue().get(userId);

        if (sseEmitter == null) {
            log.info("聊天消息推送失败uid:[{}],没有创建连接，请重试。", userId);
            throw new BaseException("聊天消息推送失败uid:[{}],没有创建连接，请重试。~");
        }
        OpenAISSEEventSourceListener eventSourceListener = new OpenAISSEEventSourceListener(sseEmitter);
        chatGLM.streamChat(chatDto.getInput(), chatDto.getHistory(), eventSourceListener);
    }
}
