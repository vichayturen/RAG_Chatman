package com.vichayturen.rag_chatman.pojo.vo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
public class ChatVo implements Serializable {
    private Long id;
    private Long userId;
    private Long libraryId;
    private String name;
    private LocalDateTime createTime;
}
