package com.ssafy.nayeogi.attraction.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "여행 계획 일자별 상세 요청")
public class PlanDetailRequest {
	@Schema(description = "포함할 관광지 ID", example = "12540")
	private Integer attractionId;
	@Schema(description = "여행 몇 번째 날인지 나타내는 일자", example = "1")
	private Integer planDate;
	@Schema(description = "해당 일자의 방문 순서", example = "2")
	private Integer sequence;
}
