package com.vichayturen.rag_chatman.chatman_service.pojo.vo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class LibraryVo implements Serializable {
    private Long id;
    private String name;
}
