package com.ssafy.nayeogi.attraction.model.dto;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "여행 계획 생성 요청")
public class PlanCreateRequest {
	@Schema(description = "생성된 여행 계획 ID", example = "10")
	private Integer id;
	@Schema(description = "여행 계획을 등록하는 회원 ID", example = "ssafy01")
	private String memberId;
	@Schema(description = "여행 계획 제목", example = "여름 부산 여행 2박 3일")
	private String title;
	@Schema(description = "여행 시작일", example = "2024-07-01")
	private LocalDate startDate;
	@Schema(description = "여행 종료일", example = "2024-07-03")
	private LocalDate endDate;
	@Schema(description = "여행 소개", example = "부산 주요 해변과 맛집 중심 일정")
	private String description;
	@Schema(description = "일정에 포함될 관광지 ID 목록", example = "[12540, 12302]")
	@JsonProperty("attractionId")
	private List<Integer> attractionIds;
	@Schema(description = "일자별 상세 일정 목록")
	private List<PlanDetailRequest> planDetails;
}
