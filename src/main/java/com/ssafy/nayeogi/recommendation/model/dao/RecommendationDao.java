package com.ssafy.nayeogi.recommendation.model.dao;

import com.ssafy.nayeogi.recommendation.model.dto.RecommendationResponse;
import com.ssafy.nayeogi.recommendation.model.dto.SurveyResultRequest;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * 추천 DB 접근을 위한 매퍼 인터페이스
 */
@Mapper
public interface RecommendationDao {
    int saveSurveyResult(SurveyResultRequest surveyResultRequest);
    List<RecommendationResponse> findRecommendationsByUser(String userId);
}
