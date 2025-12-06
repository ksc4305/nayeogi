package com.ssafy.nayeogi.member.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.nayeogi.common.dto.ApiResponse;
import com.ssafy.nayeogi.member.model.dto.MemberDto;
import com.ssafy.nayeogi.member.service.MemberService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 회원 관련 API 요청을 처리하는 Controller
 * * [수정 사항 및 주석]
 * 1. 기존의 try-catch 블록을 모두 제거했습니다. (GlobalExceptionHandler가 처리)
 * 2. 리턴 타입을 'ApiResponse'로 통일하여 팀원 간 응답 규격을 맞췄습니다.
 * * @author SSAFY
 */
@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

	private final MemberService memberService;
		/**
		 * 서버 연결 테스트용 API
		 * [GET] /api/v1/members/test
		 * 브라우저 주소창에 이 주소를 치면 성공 메시지가 나와야 합니다.
		 */
		@GetMapping("/test")
		public ResponseEntity<ApiResponse<String>> test() {
			return ResponseEntity.ok(ApiResponse.success("성환이형님! 서버 연결 대성공입니다! 🎉"));
		}
	
	/**
	 * 회원가입 API
	 * [POST] /api/v1/members/join
	 * @param memberDto 회원가입할 사용자 정보 (JSON -> DTO)
	 * @return 성공 시 201 Created와 함께 성공 메시지 반환
	 */
	@PostMapping("/join")
	public ResponseEntity<ApiResponse<Void>> join(@RequestBody MemberDto memberDto) {
		// 1. 서비스 비즈니스 로직 호출
		// (중복 아이디 등 에러 발생 시, 여기서 즉시 중단되고 GlobalExceptionHandler로 넘어갑니다.)
		memberService.join(memberDto);
		// 2. 예외 없이 여기까지 왔다면 '성공'입니다.
		// 팀원이 만든 ApiResponse.success() 메서드 사용
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(ApiResponse.success("회원가입 성공"));
	}
	@PostMapping("/login")
	public ResponseEntity<ApiResponse<MemberDto>> login(@RequestBody MemberDto memberDto, HttpSession session) {
		log.info("로그인 요청: {}", memberDto.getUserId());
		
		// 1. 서비스 호출
		// 아이디가 없거나 비번이 틀리면 서비스에서 CustomException을 던집니다.
		// 즉, 이 변수에 값이 담겼다는 건 '로그인 성공'이라는 뜻입니다.
		MemberDto loginInfo = memberService.login(memberDto.getUserId(), memberDto.getUserPassword());
		
		// 2. 성공 시 회원 정보 리턴
		// 프론트에서는 response.data 안에 있는 회원 정보를 꺼내 쓰면 됩니다.
		session.setAttribute("userInfo", loginInfo);
		log.info("세션 저장 완료 - Session ID: {}", session.getId());
		
		return ResponseEntity.ok(ApiResponse.success(loginInfo));
	}
	
	@PostMapping("/logout")
	public ResponseEntity<ApiResponse<Void>> logout(HttpSession session) {
		log.info("로그아웃 요청 - Session ID: {}", session.getId());
		
		// 세션 무효화 (저장된 모든 정보 삭제)
		session.invalidate();
		
		return ResponseEntity.ok(ApiResponse.success("로그아웃 성공"));
	}
	
}