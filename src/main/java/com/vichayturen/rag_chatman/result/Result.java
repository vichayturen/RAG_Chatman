package com.vichayturen.rag_chatman.result;

import lombok.Data;

@Data
public class Result {
    private int code;
    private String msg;
    private Object data;
}
