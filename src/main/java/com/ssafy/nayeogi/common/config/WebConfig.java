package com.ssafy.nayeogi.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**") // 모든 경로에 대해
			.allowedOrigins("http://localhost:5173") // Vue 주소 허용
			.allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS") // 모든 HTTP 메서드 허용
			.allowCredentials(true) // [중요] 세션 쿠키(JSESSIONID)를 주고받을 수 있게 허용
			.maxAge(3600);
	}
}