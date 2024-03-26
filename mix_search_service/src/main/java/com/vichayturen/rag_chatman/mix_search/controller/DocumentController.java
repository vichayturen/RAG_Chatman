package com.vichayturen.rag_chatman.mix_search.controller;

import com.vichayturen.rag_chatman.chatman_common.Result;
import com.vichayturen.rag_chatman.chatman_common.dto.InsertDto;
import com.vichayturen.rag_chatman.chatman_common.dto.QueryDto;
import com.vichayturen.rag_chatman.chatman_common.entity.DocumentEntity;
import com.vichayturen.rag_chatman.mix_search.template.MixSearchTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class DocumentController {
    @Autowired
    private MixSearchTemplate mixSearchTemplate;

    @PostMapping("/insert")
    public Result<Boolean> insert(@RequestBody InsertDto insertDto) {
        log.info("插入：{}", insertDto);
        List<String> chunks = insertDto.getChunks();
        String indexName = insertDto.getIndexName();
        mixSearchTemplate.insertDocuments(chunks, indexName);
        return Result.success();
    }

    @DeleteMapping("/delete")
    public Result<Boolean> delete(String indexName) {
        log.info("删除：{}", indexName);
        return mixSearchTemplate.cleanDocuments(indexName);
    }

    @PostMapping("/query")
    public Result<List<DocumentEntity>> query(@RequestBody QueryDto queryDto) {
        log.info("查询：{}", queryDto);
        String question = queryDto.getQuestion();
        String indexName = queryDto.getIndexName();
        int topK = queryDto.getTopK();
        return mixSearchTemplate.searchDocuments(question, indexName, topK);
    }
}
