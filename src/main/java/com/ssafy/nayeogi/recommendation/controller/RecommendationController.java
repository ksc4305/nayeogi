package com.ssafy.nayeogi.recommendation.controller;

import com.ssafy.nayeogi.recommendation.model.dto.RecommendationResponse;
import com.ssafy.nayeogi.recommendation.model.dto.SurveyResultRequest;
import com.ssafy.nayeogi.recommendation.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 추천 & 설문 관련 API 요청을 처리하는 컨트롤러
 */
@RestController
@RequestMapping("/recommendations")
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;

    /**
     * 설문 결과 제출
     * @param surveyResultRequest 설문 결과 데이터
     * @return
     */
    @PostMapping("/survey")
    public ResponseEntity<?> submitSurvey(@RequestBody SurveyResultRequest surveyResultRequest) {
        recommendationService.saveSurveyResult(surveyResultRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 사용자 맞춤 추천 목록 조회
     * @param userId 사용자 ID (세션 등에서 추출하는 것이 더 안전합니다)
     * @return 추천 관광지 목록
     */
    @GetMapping
    public ResponseEntity<List<RecommendationResponse>> getRecommendations(@RequestParam String userId) {
        List<RecommendationResponse> recommendations = recommendationService.getRecommendations(userId);
        return ResponseEntity.ok(recommendations);
    }
}
