package com.ssafy.nayeogi.recommendation.service;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.nayeogi.recommendation.model.dao.RecommendationDao;
import com.ssafy.nayeogi.recommendation.model.dto.Recommendation;
import com.ssafy.nayeogi.recommendation.model.dto.RecommendationRequest;
import com.ssafy.nayeogi.recommendation.model.dto.RecommendationResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecommendationServiceImpl implements RecommendationService {

    private final RecommendationDao recommendationDao;

    @Override
    public RecommendationResponse recommend(RecommendationRequest request) {
        List<Integer> svdIds =
            request == null || request.getSvdIds() == null
                ? Collections.emptyList()
                : request.getSvdIds();

        List<Recommendation> list = recommendationDao.selectRecommendations(svdIds);
        return new RecommendationResponse(list);
    }
}
