package com.ssafy.nayeogi.attraction.model.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PlanSearchResponse {
	private Integer id;
	private String memberId;
	private String title;
	private LocalDate startDate;
	private LocalDate endDate;
	private String description;
	private LocalDateTime createdAt;
}
