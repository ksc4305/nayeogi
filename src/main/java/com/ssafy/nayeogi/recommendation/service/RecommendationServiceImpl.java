package com.ssafy.nayeogi.recommendation.service;

import com.ssafy.nayeogi.recommendation.model.dao.RecommendationDao;
import com.ssafy.nayeogi.recommendation.model.dto.RecommendationResponse;
import com.ssafy.nayeogi.recommendation.model.dto.SurveyResultRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 추천 & 설문 관련 비즈니스 로직 처리를 위한 서비스 구현체
 */
@Service
@RequiredArgsConstructor
public class RecommendationServiceImpl implements RecommendationService {

    private final RecommendationDao recommendationDao;

    @Override
    public void saveSurveyResult(SurveyResultRequest surveyResultRequest) {
        recommendationDao.saveSurveyResult(surveyResultRequest);
    }

    @Override
    public List<RecommendationResponse> getRecommendations(String userId) {
        return recommendationDao.findRecommendationsByUser(userId);
    }
}
