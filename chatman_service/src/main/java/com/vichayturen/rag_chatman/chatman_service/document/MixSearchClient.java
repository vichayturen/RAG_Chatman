package com.vichayturen.rag_chatman.chatman_service.document;

import com.vichayturen.rag_chatman.chatman_common.Result;
import com.vichayturen.rag_chatman.chatman_common.dto.InsertDto;
import com.vichayturen.rag_chatman.chatman_common.dto.QueryDto;
import com.vichayturen.rag_chatman.chatman_common.entity.DocumentEntity;
import com.vichayturen.rag_chatman.chatman_service.exception.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Component
public class MixSearchClient {
    @Autowired
    private MixSearchFeignClient mixSearchFeignClient;

    public void insert(List<String> chunks, String indexName) throws IOException {
        InsertDto insertDto = InsertDto.builder()
                .chunks(chunks)
                .indexName(indexName)
                .build();
        Result<Boolean> result = mixSearchFeignClient.insert(insertDto);
    }

    public void delete(String indexName) throws IOException {
        Result<Boolean> result = mixSearchFeignClient.delete(indexName);
    }

    public String query(String question, String indexName) throws IOException {
        QueryDto queryDto = QueryDto.builder()
                .question(question)
                .indexName(indexName)
                .topK(10)
                .build();
        Result<List<DocumentEntity>> result = mixSearchFeignClient.query(queryDto);
        if (result.getCode() == 0) {
            String context = result.getData().get(0).getContent();
            return context;
        } else {
            throw new BaseException(result.getMsg());
        }
    }
}
