package com.ssafy.nayeogi.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // 1. 공통 에러
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류입니다."),
    FORBIDDEN_ACCESS(HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),
    UNAUTHORIZED_MEMBER(HttpStatus.UNAUTHORIZED, "로그인이 필요한 서비스입니다."),


    // 2. 회원 관련 (MEMBER_)
    MEMBER_ID_DUPLICATE(HttpStatus.BAD_REQUEST, "이미 사용 중인 아이디입니다."),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다."),
    MEMBER_PASSWORD_MISMATCH(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),

    // 3. 이미지/파일 관련 (FILE_)
    FILE_SIZE_EXCEEDED(HttpStatus.BAD_REQUEST, "파일 용량이 10MB를 초과했습니다."),
    FILE_UPLOAD_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패했습니다."), 
    
    // 4. 스토리 관련 (STORY_)
    STORY_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 스토리입니다.");
    
private final HttpStatus httpStatus;
    private final String message;
    
}