package com.vichayturen.rag_chatman.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "rag.llm")
@Data
public class LlmProperties {
    private String type;
    private String apiKey;
    private String model;
    private String url;
}
