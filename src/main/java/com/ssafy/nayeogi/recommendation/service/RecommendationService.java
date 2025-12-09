package com.ssafy.nayeogi.recommendation.service;

import com.ssafy.nayeogi.recommendation.model.dto.RecommendationRequest;
import com.ssafy.nayeogi.recommendation.model.dto.RecommendationResponse;

public interface RecommendationService {

    RecommendationResponse recommend(RecommendationRequest request);
}
