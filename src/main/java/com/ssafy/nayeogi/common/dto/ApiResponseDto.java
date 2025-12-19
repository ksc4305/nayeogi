package com.ssafy.nayeogi.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponseDto<T> {
    
    private String code;    
    private String message;
    private T data;

    // success
    // 1. 기본 메세지만 반환
    public static <T> ApiResponseDto<T> success() {
    	return new ApiResponseDto<>("SUCCESS", "요청이 성공했습니다.", null);
    }
   
    // 2. 메세지만 반환
    public static <T> ApiResponseDto<T> success(String message) {
    	return new ApiResponseDto<>("SUCCESS", message, null);
    }

    // 3. 기본 메세지 + 데이터 반환
    public static <T> ApiResponseDto<T> success(T data) {
        return new ApiResponseDto<>("SUCCESS", "요청이 성공했습니다.", data);
    }

    // 4. 데이터와 메시지 둘 다 반환
    public static <T> ApiResponseDto<T> success(String message, T data) {
    	return new ApiResponseDto<>("SUCCESS", message, data);
    }


    // error
    // 에러코드 + 메세지 반환
    public static <T> ApiResponseDto<T> error(String errorCode, String message) {
        return new ApiResponseDto<>(errorCode, message, null);
    }
}