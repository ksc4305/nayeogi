package com.ssafy.nayeogi.member.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.nayeogi.member.model.dao.MemberMapper;
import com.ssafy.nayeogi.member.model.dto.MemberDto;

import lombok.RequiredArgsConstructor;

/**
 * MemberService의 구현 클래스
 * @author SSAFY
 */
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

	private final MemberMapper memberMapper;
	
	// TODO: 비밀번호 암호화를 위해 Spring Security의 PasswordEncoder를 주입받아 사용해야 합니다.
	// 예: private final PasswordEncoder passwordEncoder;
	
	@Override
	@Transactional
	public void join(MemberDto memberDto) throws Exception {
		// 1. 아이디 중복 체크
		if(memberMapper.idCheck(memberDto.getUserId()) > 0) {
			throw new Exception("이미 사용 중인 아이디입니다.");
		}
		
		// 2. 비밀번호 암호화 (현재는 평문으로 저장)
		// String encodedPassword = passwordEncoder.encode(memberDto.getUserPassword());
		// memberDto.setUserPassword(encodedPassword);
		
		// 3. 회원 정보 저장
		memberMapper.insertMember(memberDto);
	}

	@Override
	public int idCheck(String userId) throws Exception {
		return memberMapper.idCheck(userId);
	}

}