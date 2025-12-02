package com.ssafy.nayeogi.member.model.dao;

import java.sql.SQLException;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.nayeogi.member.model.dto.MemberDto;

/**
 * 회원 관련 데이터 처리를 위한 Mapper Interface
 * @author SSAFY
 *
 */
@Mapper
public interface MemberMapper {

	/**
	 * 회원 정보를 DB에 삽입 (회원가입)
	 * @param memberDto 가입할 회원 정보
	 * @return 삽입된 행의 수
	 * @throws SQLException DB 에러
	 */
	int insertMember(MemberDto memberDto) throws SQLException;
	
	/**
	 * 아이디 중복 체크
	 * @param userId 중복 확인할 아이디
	 * @return 해당 아이디의 개수 (0 또는 1)
	 * @throws SQLException DB 에러
	 */
	int idCheck(String userId) throws SQLException;
	
}
