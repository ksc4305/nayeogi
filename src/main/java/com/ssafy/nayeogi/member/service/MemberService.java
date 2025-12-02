package com.ssafy.nayeogi.member.service;

import com.ssafy.nayeogi.member.model.dto.MemberJoinRequest;
// import com.ssafy.nayeogi.member.model.dto.MemberLoginRequest;
// import com.ssafy.nayeogi.member.model.dto.MemberDto;

/**
 * 회원 관련 비즈니스 로직 처리를 위한 서비스 인터페이스
 */
public interface MemberService {
    /**
     * 회원 가입
     * @param memberJoinRequest 회원 가입 정보
     * @return 생성된 회원 ID
     */
    Long join(MemberJoinRequest memberJoinRequest);

    /**
     * 로그인
     * @param memberLoginRequest 로그인 정보
     * @return 로그인한 회원 정보
     */
    // MemberDto login(MemberLoginRequest memberLoginRequest);
}
