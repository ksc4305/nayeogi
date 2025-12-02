package com.ssafy.nayeogi.recommendation.dto;

import com.ssafy.nayeogi.recommendation.dao.Recommendation;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecommendationResponse {

    private List<Recommendation> recommendations;
}
