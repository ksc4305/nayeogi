package com.ssafy.nayeogi.attraction.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "여행 계획 상세 응답")
public class PlanDetailResponse {
	@Schema(description = "여행 계획 ID", example = "10")
	private Integer plan_id;
	@Schema(description = "관광지 ID", example = "12540")
	private Integer attraction_id;
	@Schema(description = "여행 몇 번째 날인지 나타내는 일자", example = "1")
	private Integer plan_date;
	@Schema(description = "해당 일자의 방문 순서", example = "2")
	private Integer sequence;
	@Schema(description = "관광지 제목", example = "해운대 해수욕장")
	private String attraction_title;
}
