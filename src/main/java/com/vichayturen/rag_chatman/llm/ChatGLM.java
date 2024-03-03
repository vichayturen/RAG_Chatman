package com.vichayturen.rag_chatman.llm;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.vichayturen.rag_chatman.entity.Message;
import com.vichayturen.rag_chatman.enums.Role;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ChatGLM implements LanguageModel {
    @Value("${rag.llm.zhipu.url}")
    private String url;
    @Value("${rag.llm.zhipu.api-key}")
    private String apiKey;
    @Value("${rag.llm.zhipu.model}")
    private String model;

    @Override
    public String syncGenerate(String input, List<Message> history) {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .build();
        Map<String, Object> body = new HashMap<>();
        body.put("model", model);
        Message newMessage = new Message(Role.USER.toString(), input);
        history.add(newMessage);
        body.put("messages", history);
        RequestBody requestBody = RequestBody.create(JSON.toJSONBytes(body), MediaType.parse("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .addHeader("Authorization", "Bearer "+apiKey)
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

    @Override
    public void asyncGenerate(String input, List<Message> history) {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .build();
        Map<String, Object> body = new HashMap<>();
        body.put("model", model);
        Message newMessage = new Message(Role.USER.toString(), input);
        history.add(newMessage);
        body.put("messages", history);
        RequestBody requestBody = RequestBody.create(JSON.toJSONBytes(body), MediaType.parse("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .addHeader("Authorization", "Bearer "+apiKey)
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            JSONObject jsonObject = (JSONObject) JSON.parse(response.body().string());
            JSONArray choices = (JSONArray) jsonObject.get("choices");
            JSONObject choice = (JSONObject) choices.get(0);
            Message message = (Message) choice.get("message");
            String content = message.getContent();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
