package com.vichayturen.rag_chatman.chatman_service.pojo.entity;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
public class LibraryEntity implements Serializable {
    private Long id;
    private Long userId;
    private String indexName;
    private String name;
    private LocalDateTime createTime;
}
