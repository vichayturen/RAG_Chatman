package com.vichayturen.rag_chatman.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
public class Message implements Serializable {
    private String role;
    private String content;
}
