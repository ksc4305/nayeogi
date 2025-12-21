package com.ssafy.nayeogi.attraction.model.dto;

import java.util.Collections;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AttractionRecommendationRequest {

    private String area;
    private Integer contentTypeId;
    private List<Integer> surveyIds;

    public List<Integer> getSurveyIds() {
        if (surveyIds == null) {
            return Collections.emptyList();
        }
        return surveyIds;
    }
}
