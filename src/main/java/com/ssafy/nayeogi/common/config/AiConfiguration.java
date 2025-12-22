package com.ssafy.nayeogi.common.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.google.genai.GoogleGenAiChatModel;
import org.springframework.ai.google.genai.GoogleGenAiChatOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import com.google.genai.Client;
import com.google.genai.types.HttpOptions;

@Configuration
public class AiConfiguration {

    @Value("${spring.ai.google.genai.api-key}")
    private String genAiApiKey;

    @Value("${spring.ai.google.genai.base-url}")
    private String genAiBaseUrl;

    /**
     * SSAFY GMS의 특성(Chunked 전송 미지원 등)에 대응하기 위해 
     * 데이터를 버퍼링하여 전송하는 RestClient 설정입니다.
     */
    @Bean
    RestClient.Builder restClientBuilder() {
        return RestClient.builder()
                .requestFactory(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
    }

    /**
     * GMS 전용 Base URL과 API Key를 적용한 Gemini 모델 빈입니다.
     */
    @Bean
    ChatModel googleGenAiChatModel() {
        // API 클라이언트 생성 및 커스텀 베이스 URL 설정
        Client client = Client.builder().apiKey(genAiApiKey)
                .httpOptions(HttpOptions.builder().baseUrl(genAiBaseUrl).build())
                .build();
        
     // ChatModel 생성 시 Client 주입
        return GoogleGenAiChatModel.builder()
                .genAiClient(client)
                .defaultOptions(GoogleGenAiChatOptions.builder()
                        .model("gemini-2.0-flash") // or "gemini-2.0-flash"
                        .temperature(0.7)
                        .maxOutputTokens(1500)
                        .build())
                .build();
    }

    /**
     * 서비스에서 바로 주입받아 사용할 수 있는 전역 ChatClient입니다.
     * 기본 시스템 프롬프트를 미리 설정하여 코드를 간결하게 만듭니다.
     */
    @Bean
    ChatClient chatClient(ChatModel chatModel) {
        var loggerAdvisor = SimpleLoggerAdvisor.builder().order(Ordered.LOWEST_PRECEDENCE - 1).build();

        return ChatClient.builder(chatModel)
                .defaultAdvisors(loggerAdvisor)
                .build();
    }
}