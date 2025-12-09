package com.ssafy.nayeogi.recommendation.controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.nayeogi.recommendation.model.dto.RecommendationRequest;
import com.ssafy.nayeogi.recommendation.model.dto.RecommendationResponse;
import com.ssafy.nayeogi.recommendation.service.RecommendationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/recommendations")
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;

    @PostMapping
    public RecommendationResponse recommend(@RequestBody RecommendationRequest request) {
        return recommendationService.recommend(request);
    }
}
