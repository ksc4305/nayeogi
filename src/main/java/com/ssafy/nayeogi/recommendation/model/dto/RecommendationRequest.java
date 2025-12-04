package com.ssafy.nayeogi.recommendation.model.dto;

import java.util.Collections;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RecommendationRequest {

    private SurveyRequest survey;

    public List<Integer> getSvdIds() {
        if (survey == null || survey.getSvdIds() == null) {
            return Collections.emptyList();
        }
        return survey.getSvdIds();
    }

    @Data
    @NoArgsConstructor
    public static class SurveyRequest {

        private List<Integer> kmeansIds;
        private List<Integer> svdIds;
    }
}
