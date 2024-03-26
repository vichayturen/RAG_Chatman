package com.vichayturen.rag_chatman.chatman_service.llm;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Objects;

/**
 * 描述：OpenAIEventSourceListener
 *
 * @author https:www.unfbx.com
 * @date 2023-02-22
 */
@Slf4j
public class LocalLlmEventSourceListener extends EventSourceListener {

    private long tokens;

    private SseEmitter sseEmitter;

    public LocalLlmEventSourceListener(SseEmitter sseEmitter) {
        this.sseEmitter = sseEmitter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onOpen(EventSource eventSource, Response response) {
        log.info("Local LLM建立sse连接...");
    }

    /**
     * {@inheritDoc}
     */
    @SneakyThrows
    @Override
    public void onEvent(EventSource eventSource, String id, String type, String data) {
        log.info("Local LLM返回数据：{}；{}；{}", id, type, data);
        tokens += 1;
        if (data.equals("[DONE]")) {
            log.info("Local LLM返回数据结束了");
            sseEmitter.send(SseEmitter.event()
                    .id("[TOKENS]")
                    .data("<br/><br/>tokens：" + tokens())
                    .reconnectTime(3000));
            sseEmitter.send(SseEmitter.event()
                    .id("[DONE]")
                    .data("[DONE]")
                    .reconnectTime(3000));
            // 传输完成后自动关闭sse
            sseEmitter.complete();
            return;
        }
        JSONObject responseJson = JSON.parseObject(data);
        try {
            sseEmitter.send(SseEmitter.event()
                    .id((String) responseJson.get("id"))
                    .data(responseJson)
                    .reconnectTime(3000));
        } catch (Exception e) {
            log.error("sse信息推送失败！");
            eventSource.cancel();
            e.printStackTrace();
        }
    }


    @Override
    public void onClosed(EventSource eventSource) {
        log.info("流式输出返回值总共{}tokens", tokens());
        log.info("Local LLM关闭sse连接...");
    }


    @SneakyThrows
    @Override
    public void onFailure(EventSource eventSource, Throwable t, Response response) {
        if (Objects.isNull(response)) {
            return;
        }
        ResponseBody body = response.body();
        if (Objects.nonNull(body)) {
            log.error("Local LLM  sse连接异常data：{}，异常：{}", body.string(), t);
        } else {
            log.error("Local LLM  sse连接异常data：{}，异常：{}", response, t);
        }
        eventSource.cancel();
    }

    /**
     * tokens
     * @return
     */
    public long tokens() {
        return tokens;
    }
}
