package com.vichayturen.rag_chatman.chatman_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ChatmanServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChatmanServiceApplication.class, args);
	}

}
