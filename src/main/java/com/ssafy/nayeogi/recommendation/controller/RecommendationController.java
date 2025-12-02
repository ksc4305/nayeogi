package com.ssafy.nayeogi.recommendation.controller;

import com.ssafy.nayeogi.recommendation.dto.RecommendationRequest;
import com.ssafy.nayeogi.recommendation.dto.RecommendationResponse;
import com.ssafy.nayeogi.recommendation.service.RecommendationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/recommendations")
public class RecommendationController {

    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @PostMapping
    public RecommendationResponse recommend(@RequestBody RecommendationRequest request) {
        return recommendationService.recommend(request);
    }
}
