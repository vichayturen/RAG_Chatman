package com.vichayturen.rag_chatman.pojo.entity;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class ChatCompletion implements Serializable {
    private String model;
    private List<Message> messages;
}
