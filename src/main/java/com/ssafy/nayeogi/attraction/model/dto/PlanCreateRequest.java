package com.ssafy.nayeogi.attraction.model.dto;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class PlanCreateRequest {
	private Integer id;
	private String memberId;
	private String title;
	private LocalDate startDate;
	private LocalDate endDate;
	private String description;
	@JsonProperty("attractionId")
	private List<Integer> attractionIds;
}
