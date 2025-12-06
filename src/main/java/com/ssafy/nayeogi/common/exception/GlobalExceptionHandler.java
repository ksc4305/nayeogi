package com.ssafy.nayeogi.common.exception;

import com.ssafy.nayeogi.common.dto.ApiResponse;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
	
	/**
     * 1. 비즈니스 로직 에러 (우리가 직접 발생시킨 에러)
     */
	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ApiResponse<Void>> handleCustomException(CustomException e) {
	    ErrorCode errorCode = e.getErrorCode();
	    
	    // 로그에는 상세하게
	    log.error("CustomException: name={}, message={}", errorCode.name(), errorCode.getMessage());
	    
	    return ResponseEntity
	            .status(errorCode.getHttpStatus())
	            // errorCode.name() -> ENUM으로 설정한 예외 이름 반환
	            .body(ApiResponse.error(errorCode.name(), errorCode.getMessage()));
	}
	
	/**
     * 2. 유효성 검사 실패 (@Valid, @Validated)
     * - MemberDto 필드 검증 실패 시 발생
     * - 팀원에게 전달 -> pom에 valid 의존성 추가 -> dto에 설정 -> 컨트롤러에 @Valid 설정 학습 필요
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        StringBuilder builder = new StringBuilder();
        
        // 에러 메시지를 하나로 합침 (예: "이메일은 필수입니다. 비밀번호는 8자 이상이어야 합니다.")
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            builder.append(fieldError.getDefaultMessage()).append(" ");
        }
        
        String errorMessage = builder.toString().trim();
        log.error("Validation Error: {}", errorMessage);

        // BAD_REQUEST 코드로 응답
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(ErrorCode.BAD_REQUEST.name(), errorMessage));
    }

    /**
     * 3. 파일 용량 초과
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ApiResponse<Void>> handleMaxSizeException(MaxUploadSizeExceededException e) {
        log.error("File Size Exceeded: {}", e.getMessage());
        
        return ResponseEntity
                .status(ErrorCode.FILE_SIZE_EXCEEDED.getHttpStatus())
                .body(ApiResponse.error(ErrorCode.FILE_SIZE_EXCEEDED.name(), ErrorCode.FILE_SIZE_EXCEEDED.getMessage()));
    }

    /**
     * 4. 그 외 모든 예상치 못한 서버 에러
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception e) {
        log.error("Unhandled Exception: ", e); //(디버깅용)
        
        return ResponseEntity
                .status(ErrorCode.SERVER_ERROR.getHttpStatus())
                .body(ApiResponse.error(ErrorCode.SERVER_ERROR.name(), ErrorCode.SERVER_ERROR.getMessage()));
    }
}