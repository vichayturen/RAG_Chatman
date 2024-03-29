package com.vichayturen.rag_chatman.chatman_service.pojo.entity;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
public class ChatEntity implements Serializable {
    private Long id;
    private Long userId;
    private Long libraryId;
    private String name;
    private LocalDateTime createTime;
}
