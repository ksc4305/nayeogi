package com.ssafy.nayeogi.attraction.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PlanDetailResponse {
	private Integer plan_id;
	private Integer attraction_id;
	private Integer plan_date;
	private Integer sequence;
}
