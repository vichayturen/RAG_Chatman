package com.vichayturen.rag_chatman.document;

import cn.hutool.http.ContentType;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
public class MixSearchClient {
    @Value("${rag.mixsearch.host}")
    private String host;
    @Value("${rag.mixsearch.port}")
    private int port;

    public void insert(List<String> chunks, String indexName) throws IOException {
        OkHttpClient okHttpClient = getOkHttpClient();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("chunks", chunks);
        jsonObject.put("indexName", indexName);
        RequestBody requestBody = RequestBody.create(jsonObject.toJSONString(), MediaType.parse(ContentType.JSON.getValue()));
        Request request = new Request.Builder()
                .url(getUrl("insert"))
                .post(requestBody)
                .build();
        Response response = okHttpClient.newCall(request).execute();
        // TODO:需要做什么？
        response.close();
    }

    public void delete(String indexName) throws IOException {
        OkHttpClient okHttpClient = getOkHttpClient();
        HttpUrl url = new HttpUrl.Builder()
                .scheme("http")
                .host(host)
                .port(port)
                .addPathSegment("delete")
                .addQueryParameter("indexName", indexName)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .delete()
                .build();
        Response response = okHttpClient.newCall(request).execute();
        // TODO:需要做什么？
        response.close();
    }

    public String query(String question, String indexName) throws IOException {
        OkHttpClient okHttpClient = getOkHttpClient();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("question", question);
        jsonObject.put("indexName", indexName);
        RequestBody requestBody = RequestBody.create(jsonObject.toJSONString(), MediaType.parse(ContentType.JSON.getValue()));
        Request request = new Request.Builder()
                .url(getUrl("query"))
                .post(requestBody)
                .build();
        try (Response response = okHttpClient.newCall(request).execute()) {
            if (response.body() != null) {
                JSONObject responseJson = JSON.parseObject(response.body().string());
                if (responseJson.getObject("code", Integer.class) == 0) {
                    JSONArray jsonArray = responseJson.getJSONArray("data");
                    return jsonArray.getObject(0, String.class);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public HttpUrl getUrl(String path) {
        return new HttpUrl.Builder()
                .scheme("http")
                .host(host)
                .port(port)
                .addPathSegment(path)
                .build();
    }

    private OkHttpClient getOkHttpClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
    }
}
