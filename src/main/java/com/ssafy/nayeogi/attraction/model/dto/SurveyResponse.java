package com.ssafy.nayeogi.attraction.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SurveyResponse {
	private Integer id;
	private String question;
	private String type;
}
