package com.ssafy.nayeogi.member.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// [중요] 글로벌 예외 처리를 위한 import
import com.ssafy.nayeogi.common.exception.CustomException;
import com.ssafy.nayeogi.common.exception.ErrorCode;

// [중요] MemberMapper가 아닌 MemberDao를 import 합니다.
import com.ssafy.nayeogi.member.model.dao.MemberDao;
import com.ssafy.nayeogi.member.model.dto.MemberDto;

import lombok.RequiredArgsConstructor;

/**
 * 회원 비즈니스 로직 구현체
 * * [수정 사항]
 * 1. MemberDao 인터페이스를 주입받아 사용합니다.
 * 2. throws Exception을 제거하고, 예외 발생 시 CustomException을 던집니다.
 * 3. 이렇게 던져진 예외는 GlobalExceptionHandler가 받아서 처리합니다.
 * * @author SSAFY
 */
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

	// 형님이 만드신 MemberDao 인터페이스 주입
	private final MemberDao memberDao;
	
	// TODO: 추후 PasswordEncoder 주입 받아 비밀번호 암호화 적용 필요
	// private final PasswordEncoder passwordEncoder;
	
	/**
	 * 회원가입 비즈니스 로직
	 */
	@Override
	@Transactional
	public void join(MemberDto memberDto) {
		
		// 1. 아이디 중복 체크
		// DAO를 통해 DB 조회 -> 0보다 크면 이미 있는 아이디입니다.
		if(memberDao.idCheck(memberDto.getUserId()) > 0) {
			// [핵심] 여기서 에러를 던지면 컨트롤러로 가지 않고 바로 핸들러로 갑니다.
			// "이미 사용 중인 아이디입니다."라는 메시지를 가진 에러 코드를 전달합니다.
			throw new CustomException(ErrorCode.MEMBER_ID_DUPLICATE);
		}
		
		// 2. 비밀번호 암호화 (추후 적용할 자리)
		// memberDto.setUserPassword(passwordEncoder.encode(memberDto.getUserPassword()));
		
			memberDao.insertMember(memberDto);
	}

	/**
	 * 아이디 중복 체크 (단순 조회)
	 */
	@Override
	public int idCheck(String userId) {
		return memberDao.idCheck(userId);
	}

	@Override
	public MemberDto login(String userId, String userPassword) {
		
		MemberDto memberInfo = memberDao.memberInfo(userId);
		
		if (memberInfo == null) {
			throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
		}
		// 3. 비밀번호 비교 (DB비번 vs 입력비번)
		if (!memberInfo.getUserPassword().equals(userPassword)) {
			throw new CustomException(ErrorCode.MEMBER_PASSWORD_MISMATCH);
		}
		
		// 4. 검증 완료된 회원 정보 리턴
		return memberInfo;
	}
	
	

}