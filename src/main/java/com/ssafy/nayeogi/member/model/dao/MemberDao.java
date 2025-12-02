package com.ssafy.nayeogi.member.model.dao;

import org.apache.ibatis.annotations.Mapper;

/**
 * 회원 DB 접근을 위한 매퍼 인터페이스
 * @param <MemberJoinRequest>
 */
@Mapper
public interface MemberDao<MemberJoinRequest> {
    int insertMember(MemberJoinRequest memberJoinRequest);
    // MemberDto login(MemberLoginRequest memberLoginRequest);
    // MemberDto findById(String userId);
    int updateMember(String userId);
    int deleteMember(String userId);
}
