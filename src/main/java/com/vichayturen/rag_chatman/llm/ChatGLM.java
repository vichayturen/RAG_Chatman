package com.vichayturen.rag_chatman.llm;

import cn.hutool.http.ContentType;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.vichayturen.rag_chatman.pojo.entity.ChatCompletion;
import com.vichayturen.rag_chatman.pojo.entity.Message;
import com.vichayturen.rag_chatman.constant.enums.Role;
import com.vichayturen.rag_chatman.properties.LlmProperties;
import okhttp3.*;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import okhttp3.sse.EventSources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class ChatGLM implements LanguageModel {
    @Autowired
    private LlmProperties llmProperties;

    @Override
    public void streamChat(String input, List<Message> history, EventSourceListener eventSourceListener) {
        OkHttpClient okHttpClient = getOkHttpClient();
        EventSource.Factory eventSourceFactory = EventSources.createFactory(okHttpClient);
        Message newMessage = new Message(Role.USER.toString(), input);
        history.add(newMessage);
        ChatCompletion chatCompletion = ChatCompletion.builder()
                .model(llmProperties.getModel())
                .messages(history)
                .build();
        RequestBody requestBody = RequestBody.create(JSON.toJSONBytes(chatCompletion), MediaType.parse(ContentType.JSON.getValue()));
        Request request = new Request.Builder()
                .url(llmProperties.getUrl())
                .post(requestBody)
                .addHeader("Authorization", "Bearer "+llmProperties.getApiKey())
                .build();
        EventSource eventSource = eventSourceFactory.newEventSource(request, eventSourceListener);
    }

    @Override
    public String chat(String input, List<Message> history) {
        OkHttpClient okHttpClient = getOkHttpClient();
        Message newMessage = new Message(Role.USER.toString(), input);
        history.add(newMessage);
        ChatCompletion chatCompletion = ChatCompletion.builder()
                .model(llmProperties.getModel())
                .messages(history)
                .build();
        RequestBody requestBody = RequestBody.create(JSON.toJSONBytes(chatCompletion), MediaType.parse(ContentType.JSON.getValue()));
        Request request = new Request.Builder()
                .url(llmProperties.getUrl())
                .post(requestBody)
                .addHeader("Authorization", "Bearer "+llmProperties.getApiKey())
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            JSONObject jsonObject = (JSONObject) JSON.parse(response.body().string());
            JSONArray choices = (JSONArray) jsonObject.get("choices");
            JSONObject choice = (JSONObject) choices.get(0);
            Message message = (Message) choice.get("message");
            String content = message.getContent();
            return content;
        } catch (IOException e) {
            e.printStackTrace();
        }
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
