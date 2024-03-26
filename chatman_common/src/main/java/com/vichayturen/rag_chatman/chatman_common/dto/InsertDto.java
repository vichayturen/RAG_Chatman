package com.vichayturen.rag_chatman.chatman_common.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class InsertDto {
    private List<String> chunks;
    private String indexName;
}
