package com.ssafy.nayeogi.member.service;

import com.ssafy.nayeogi.member.model.dto.MemberDto;

/**
 * 회원 관련 비즈니스 로직 처리를 위한 Service Interface
 * @author SSAFY
 *
 */
public interface MemberService {

	/**
	 * 회원가입
	 * @param memberDto 가입할 회원 정보
	 * @throws Exception 아이디 중복 시 예외 발생
	 */
	void join(MemberDto memberDto) ;
	
	/**
	 * 아이디 중복 체크
	 * @param userId 중복 확인할 아이디
	 * @return 해당 아이디의 개수 (0 또는 1)
	 * @throws Exception 예외
	 */
	int idCheck(String userId) ;
	
	/**
	 * 로그인 처리
	 * @param userId 입력받은 아이디
	 * @param userPassword 입력받은 비밀번호
	 * @return 로그인 성공 시 회원정보(MemberDto), 실패 시 null
	 */
	MemberDto login(String userId, String userPassword);
	
}
