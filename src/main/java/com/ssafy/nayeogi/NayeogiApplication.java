package com.ssafy.nayeogi;

import org.springframework.ai.model.google.genai.autoconfigure.chat.GoogleGenAiChatAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = GoogleGenAiChatAutoConfiguration.class)
public class NayeogiApplication {

	public static void main(String[] args) {
		SpringApplication.run(NayeogiApplication.class, args);
	}

}
