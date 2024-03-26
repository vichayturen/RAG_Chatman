package com.vichayturen.rag_chatman.chatman_service.web.service;

import com.vichayturen.rag_chatman.chatman_service.pojo.dto.ChatDto;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * 描述：
 *
 * @author https:www.unfbx.com
 * @date 2023-04-08
 */
public interface SseService {
    /**
     * 创建SSE
     * @param chatId
     * @return
     */
    SseEmitter createSse(Long chatId);

    /**
     * 关闭SSE
     * @param chatId
     */
    void closeSse(Long chatId);

    /**
     * 客户端发送消息到服务端
     *
     * @param chatDto
     * @return
     */
    int sseChat(Long userId, ChatDto chatDto);
}
