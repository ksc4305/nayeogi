package com.ssafy.nayeogi.member.controller;

import com.ssafy.nayeogi.member.model.dto.MemberJoinRequest;
import com.ssafy.nayeogi.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 회원 인증(가입, 로그인) 관련 API 요청을 처리하는 컨트롤러
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final MemberService memberService;

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody MemberJoinRequest memberJoinRequest) {
        try {
            Long memberId = memberService.join(memberJoinRequest);
            // 회원가입 성공 시, 생성된 리소스의 ID와 함께 201 Created 응답 반환
            return ResponseEntity.status(HttpStatus.CREATED).body(memberId);
        } catch (Exception e) {
            // 예외 발생 시, 500 Internal Server Error 응답 반환
            // GlobalExceptionHandler에서 처리하는 것이 더 좋습니다.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원가입 중 오류 발생");
        }
    }

    // @PostMapping("/login")
    // public ResponseEntity<?> login(@RequestBody MemberLoginRequest memberLoginRequest) {
    //     try {
    //         MemberDto member = memberService.login(memberLoginRequest);
    //         if (member != null) {
    //             // 로그인 성공 시, 세션 생성 및 회원 정보 반환
    //             return ResponseEntity.ok(member);
    //         } else {
    //             // 로그인 실패 시, 401 Unauthorized 응답 반환
    //             return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패");
    //         }
    //     } catch (Exception e) {
    //         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("로그인 중 오류 발생");
    //     }
    // }
}
