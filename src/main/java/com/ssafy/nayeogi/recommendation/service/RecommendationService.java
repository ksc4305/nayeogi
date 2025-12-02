package com.ssafy.nayeogi.recommendation.service;

import com.ssafy.nayeogi.recommendation.dto.RecommendationRequest;
import com.ssafy.nayeogi.recommendation.dto.RecommendationResponse;

public interface RecommendationService {

    RecommendationResponse recommend(RecommendationRequest request);
}
