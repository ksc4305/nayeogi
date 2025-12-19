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
	private String member_id;
	private String title;
	private LocalDate start_date;
	private LocalDate end_date;
	private String description;
	private LocalDateTime created_at;
}
