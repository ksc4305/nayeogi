package com.ssafy.nayeogi.member.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.nayeogi.member.model.dto.MemberDto;
import com.ssafy.nayeogi.member.service.MemberService;

import lombok.RequiredArgsConstructor;

/**
 * 회원 관련 API 요청을 처리하는 Controller
 * @author SSAFY
 */
@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;
	
	/**
	 * 회원가입 API
	 * @param memberDto 회원가입 정보 (JSON)
	 * @return 성공: 201 Created, 실패: 400 Bad Request
	 */
	@PostMapping("/join")
	public ResponseEntity<?> join(@RequestBody MemberDto memberDto) {
		try {
			memberService.join(memberDto);
			return ResponseEntity.status(HttpStatus.CREATED).build();
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
}
