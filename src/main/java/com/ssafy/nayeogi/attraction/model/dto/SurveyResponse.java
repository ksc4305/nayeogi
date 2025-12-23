package com.ssafy.nayeogi.attraction.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(description = "여행 취향 설문 문항 응답")
public class SurveyResponse {
	@Schema(description = "문항 ID", example = "1")
	private Integer id;
	@Schema(description = "문항 내용", example = "맛집탐방")
	private String question;
	@Schema(description = "문항 타입", example = "SVD")
	private String type;
}
