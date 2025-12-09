package com.ssafy.nayeogi.attraction.model.dto;

import lombok.Data;

@Data
public class PlanDetailRequest {
	private final Integer attractionId;
	private final Integer sequence;
}
