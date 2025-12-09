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

	private String userId;
	private String userName;
	private String userPassword;
	private String email;
	private String joinDate;
	private String role;
	
}
