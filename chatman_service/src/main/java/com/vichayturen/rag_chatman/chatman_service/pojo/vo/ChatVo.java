package com.vichayturen.rag_chatman.chatman_service.pojo.vo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
public class ChatVo implements Serializable {
    private Long id;
    private String name;
    private Long libraryId;
    private String libraryName;
}
