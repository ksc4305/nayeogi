package com.ssafy.nayeogi.recommendation.service;

import com.ssafy.nayeogi.recommendation.model.dto.RecommendationResponse;
import com.ssafy.nayeogi.recommendation.model.dto.SurveyResultRequest;

import java.util.List;

/**
 * 추천 & 설문 관련 비즈니스 로직 처리를 위한 서비스 인터페이스
 */
public interface RecommendationService {

    /**
     * 설문 결과 저장
     * @param surveyResultRequest 설문 결과
     */
    void saveSurveyResult(SurveyResultRequest surveyResultRequest);

    /**
     * 사용자 기반 추천 목록 조회
     * @param userId 사용자 ID
     * @return 추천 목록
     */
    List<RecommendationResponse> getRecommendations(String userId);
}
