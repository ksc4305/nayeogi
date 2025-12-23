package com.ssafy.nayeogi.attraction.model.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "여행 계획 목록 응답")
public class PlanSearchResponse {
	@Schema(description = "여행 계획 ID", example = "10")
	private Integer id;
	@Schema(description = "회원 ID", example = "ssafy01")
	private String member_id;
	@Schema(description = "여행 계획 제목", example = "여름 부산 여행 2박 3일")
	private String title;
	@Schema(description = "여행 시작일", example = "2024-07-01")
	private LocalDate start_date;
	@Schema(description = "여행 종료일", example = "2024-07-03")
	private LocalDate end_date;
	@Schema(description = "여행 소개", example = "부산 주요 해변과 맛집 중심 일정")
	private String description;
	@Schema(description = "계획 생성 일시", example = "2024-06-20T14:32:00")
	private LocalDateTime created_at;
}
