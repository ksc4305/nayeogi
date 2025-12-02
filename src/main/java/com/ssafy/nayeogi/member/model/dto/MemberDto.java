package com.ssafy.nayeogi.member.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 회원 정보를 담는 DTO (Data Transfer Object)
 * API 요청/응답 및 서비스 계층에서 사용됩니다.
 * @author SSAFY
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MemberDto {

	/**
	 * 사용자 아이디 (DB 컬럼: id)
	 */
	private String userId;
	
	/**
	 * 사용자 이름 (DB 컬럼: name)
	 */
	private String userName;
	
	/**
	 * 사용자 비밀번호 (DB 컬럼: pw)
	 */
	private String userPassword;
	
	/**
	 * 사용자 이메일 (DB 컬럼: email)
	 */
	private String email;
	
	/**
	 * 가입일 (DB 컬럼: join_date)
	 */
	private String joinDate;
	
	/**
	 * 사용자 권한 (DB 컬럼: role)
	 */
	private String role;
	
}
