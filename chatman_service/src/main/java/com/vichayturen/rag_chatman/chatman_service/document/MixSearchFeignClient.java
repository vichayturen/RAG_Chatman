package com.vichayturen.rag_chatman.chatman_service.document;

import cn.hutool.http.ContentType;
import com.alibaba.fastjson.JSONObject;
import com.vichayturen.rag_chatman.chatman_common.Result;
import com.vichayturen.rag_chatman.chatman_common.dto.InsertDto;
import com.vichayturen.rag_chatman.chatman_common.dto.QueryDto;
import com.vichayturen.rag_chatman.chatman_common.entity.DocumentEntity;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "mix-search-service")
public interface MixSearchFeignClient {
    @PostMapping("/insert")
    public Result<Boolean> insert(@RequestBody InsertDto insertDto);

    @DeleteMapping("/delete")
    public Result<Boolean> delete(String indexName);

    @PostMapping("/query")
    public Result<List<DocumentEntity>> query(@RequestBody QueryDto queryDto);
}
