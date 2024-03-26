package com.vichayturen.rag_chatman.chatman_service.llm;

import cn.hutool.http.ContentType;
import com.alibaba.fastjson.JSON;
import com.vichayturen.rag_chatman.chatman_service.pojo.entity.ChatCompletion;
import com.vichayturen.rag_chatman.chatman_service.pojo.entity.Message;
import com.vichayturen.rag_chatman.chatman_service.enumeration.Role;
import com.vichayturen.rag_chatman.chatman_service.properties.LlmProperties;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import okhttp3.sse.EventSources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class LocalLlm implements LanguageModel {
    @Autowired
    private LlmProperties llmProperties;

    @Override
    public void streamChat(String input, List<Message> history, EventSourceListener eventSourceListener) {
        OkHttpClient okHttpClient = getOkHttpClient();
        EventSource.Factory eventSourceFactory = EventSources.createFactory(okHttpClient);
        Message newMessage = new Message(Role.user.toString(), input);
        history.add(newMessage);
        ChatCompletion chatCompletion = ChatCompletion.builder()
                .messages(history)
                .build();
        RequestBody requestBody = RequestBody.create(JSON.toJSONBytes(chatCompletion), MediaType.parse(ContentType.JSON.getValue()));
        Request request = new Request.Builder()
                .url(llmProperties.getUrl())
                .post(requestBody)
                .build();
        EventSource eventSource = eventSourceFactory.newEventSource(request, eventSourceListener);
    }

    @Override
    public String chat(String input, List<Message> history) {
        return null;
    }

    private OkHttpClient getOkHttpClient() {
        return new OkHttpClient().newBuilder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
    }
}
