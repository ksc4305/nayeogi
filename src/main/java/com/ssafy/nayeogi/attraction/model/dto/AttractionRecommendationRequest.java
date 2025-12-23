package com.ssafy.nayeogi.attraction.model.dto;

import java.util.Collections;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(description = "관광지 추천 요청 정보")
public class AttractionRecommendationRequest {

    @Schema(description = "선호 지역명", example = "서울")
    private String area;

    @Schema(description = "콘텐츠 타입 ID", example = "12")
    private Integer contentTypeId;

    @Schema(description = "선택된 설문 문항 ID 목록", example = "[1, 3, 5]")
    private List<Integer> surveyIds;

    public List<Integer> getSurveyIds() {
        if (surveyIds == null) {
            return Collections.emptyList();
        }
        return surveyIds;
    }
}
