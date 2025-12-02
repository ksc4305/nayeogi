package com.ssafy.nayeogi.member.service;

import com.ssafy.nayeogi.member.model.dao.MemberDao;
import com.ssafy.nayeogi.member.model.dto.MemberJoinRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 회원 관련 비즈니스 로직 처리를 위한 서비스 구현체
 */
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberDao memberDao;

    @Override
    public Long join(MemberJoinRequest memberJoinRequest) {
        // 비밀번호 암호화, 아이디 중복 검사 등 추가 로직이 필요할 수 있습니다.
        // 현재는 단순히 DAO를 호출하는 기본 구조입니다.
        memberDao.insertMember(memberJoinRequest);
        
        // insert 시 생성된 PK를 반환해야 하지만, 현재 MemberJoinRequest에 ID 필드가 없으므로 null을 반환합니다.
        // 실제 구현 시에는 DTO에 id 필드를 추가하고 mybatis의 useGeneratedKeys 옵션을 사용해야 합니다.
        return null; 
    }

    // @Override
    // public MemberDto login(MemberLoginRequest memberLoginRequest) {
    //     // 로그인 로직 구현 (e.g., 비밀번호 확인)
    //     return memberDao.login(memberLoginRequest);
    // }
}
