package com.vichayturen.rag_chatman.chatman_common.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QueryDto {
    private String question;
    private String indexName;
    private int topK;
}
