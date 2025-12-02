package com.ssafy.nayeogi.recommendation.service;

import com.ssafy.nayeogi.recommendation.dao.Recommendation;
import com.ssafy.nayeogi.recommendation.dto.RecommendationRequest;
import com.ssafy.nayeogi.recommendation.dto.RecommendationResponse;
import com.ssafy.nayeogi.recommendation.mapper.RecommendationMapper;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class RecommendationServiceImpl implements RecommendationService {

    private final RecommendationMapper recommendationMapper;

    public RecommendationServiceImpl(RecommendationMapper recommendationMapper) {
        this.recommendationMapper = recommendationMapper;
    }

    @Override
    public RecommendationResponse recommend(RecommendationRequest request) {
        List<Integer> svdIds = request == null ? Collections.emptyList() : request.getSvdIds();
        List<Recommendation> recommendations = recommendationMapper.selectRecommendations(svdIds);
        return new RecommendationResponse(recommendations);
    }
}
