package com.ssafy.nayeogi.attraction.model.dto;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "여행 계획 수정 요청")
public class PlanUpdateRequest {
	@Schema(description = "수정할 여행 계획 제목", example = "여름 부산 여행 (업데이트)")
	private String title;
	@Schema(description = "수정할 여행 시작일", example = "2024-07-02")
	private LocalDate startDate;
	@Schema(description = "수정할 여행 종료일", example = "2024-07-04")
	private LocalDate endDate;
	@Schema(description = "수정할 여행 소개", example = "휴양 위주 일정으로 변경")
	private String description;
	@Schema(description = "갱신할 관광지 ID 목록", example = "[12540, 12701]")
	@JsonProperty("attractionId")
	private List<Integer> attractionIds;
	@Schema(description = "갱신할 일자별 상세 일정 목록 (전체 교체)", example = "[{\"attractionId\":12540,\"planDate\":1,\"sequence\":1}]")
	private List<PlanDetailRequest> planDetails;
}
