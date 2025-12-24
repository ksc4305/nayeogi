package com.ssafy.nayeogi.common.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.google.genai.GoogleGenAiChatModel;
import org.springframework.ai.google.genai.GoogleGenAiChatOptions;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;

import com.google.genai.Client;
import com.google.genai.types.HttpOptions;

@Configuration
public class AiConfig {

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
    @ConditionalOnProperty(name = "app.ai.provider", havingValue = "gemini")
    ChatModel googleGenAiChatModel(
            @Value("${spring.ai.google.genai.api-key}") String apiKey,
            @Value("${spring.ai.google.genai.base-url}") String baseUrl,
            @Value("${spring.ai.google.genai.chat.options.model}") String model) {

        Client client = Client.builder()
                .apiKey(apiKey)
                .httpOptions(HttpOptions.builder().baseUrl(baseUrl).build())
                .build();
        
        return GoogleGenAiChatModel.builder()
                .genAiClient(client)
                .defaultOptions(GoogleGenAiChatOptions.builder()
                        .model(model)
                        .temperature(0.7)
                        .build())
                .build();
    }
    
    @Bean
    @ConditionalOnProperty(name = "app.ai.provider", havingValue = "openai")
    ChatModel openAiChatModel(
    		@Value("${spring.ai.openai.api-key}") String apiKey,
            @Value("${spring.ai.openai.base-url}") String baseUrl,
            @Value("${spring.ai.openai.chat.options.model}") String model,
            RestClient.Builder restClientBuilder) {
        
        // OpenAiApi에 GMS URL과 RestClient 주입
    	OpenAiApi api = OpenAiApi.builder()
                .baseUrl(baseUrl)
                .apiKey(apiKey)
                .restClientBuilder(restClientBuilder)
                .webClientBuilder(WebClient.builder()) // WebClient 빌더도 필수
                .build();

        // 2. ChatModel 생성 (Builder 사용)
        // 복잡한 생성자 대신 builder()를 사용합니다.
        return OpenAiChatModel.builder()
                .openAiApi(api)
                .defaultOptions(OpenAiChatOptions.builder()
                        .model(model)
                        .temperature(0.7)
                        .build())
//                .retryTemplate(RetryTemplate.defaultInstance()) // 재시도 설정 (선택 사항)
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