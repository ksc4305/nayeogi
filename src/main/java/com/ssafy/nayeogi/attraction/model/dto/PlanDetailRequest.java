package com.ssafy.nayeogi.attraction.model.dto;

import lombok.Data;

@Data
public class PlanDetailRequest {
	private Integer attractionId;
	private Integer planDate;
	private Integer sequence;
}
