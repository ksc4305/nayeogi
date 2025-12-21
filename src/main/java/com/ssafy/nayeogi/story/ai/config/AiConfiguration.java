package com.ssafy.nayeogi.story.ai.config;

import org.springframework.context.annotation.Configuration;

import com.google.api.client.util.Value;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class AiConfiguration {

    @Value("${spring.ai.google.genai.api-key}")
    private String genAiApiKey;

    @Value("${spring.ai.google.genai.base-url}")
    private String genAiBaseUrl;
}
