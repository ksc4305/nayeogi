package com.ssafy.nayeogi.attraction.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.nayeogi.attraction.model.dto.AttractionResponse;
import com.ssafy.nayeogi.attraction.model.dto.AttractionRecommendationRequest;
import com.ssafy.nayeogi.attraction.model.dto.AttractionRecommendationResponse;
import com.ssafy.nayeogi.attraction.model.dto.PlanCreateRequest;
import com.ssafy.nayeogi.attraction.model.dto.PlanDetailResponse;
import com.ssafy.nayeogi.attraction.model.dto.PlanSearchResponse;
import com.ssafy.nayeogi.attraction.model.dto.PlanUpdateRequest;
import com.ssafy.nayeogi.attraction.model.dto.SurveyResponse;
import com.ssafy.nayeogi.attraction.service.AttractionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Attraction API", description = "여행지 추천, 검색 및 여행 계획 관리 API")
public class AttractionController {

    private final AttractionService attractionService;

    @Operation(summary = "여행지 추천", description = "지역, 콘텐츠 타입, 설문 응답을 기반으로 추천 관광지 목록을 반환합니다.")
    @PostMapping("/recommendations")
    public ResponseEntity<List<AttractionRecommendationResponse>> recommendAttractions(
            @RequestBody AttractionRecommendationRequest request) {
        List<AttractionRecommendationResponse> recommendations = attractionService.recommendAttractions(request);
        return ResponseEntity.ok(recommendations);
    }

    @Operation(summary = "여행지 검색", description = "관광지 제목과 콘텐츠 타입 조건으로 검색합니다.")
    @GetMapping("/attractions")
    public ResponseEntity<List<AttractionResponse>> searchAttractions(
            @Parameter(description = "관광지 제목 키워드", example = "해운대")
            @RequestParam(required = false) String title,
            @Parameter(description = "콘텐츠 타입 ID 목록", example = "[12, 14]")
            @RequestParam(name = "contentTypeId", required = false) List<Integer> contentTypeIds) {

        List<AttractionResponse> attractions = attractionService.searchAttractions(title, contentTypeIds);
        return ResponseEntity.ok(attractions);
    }

    @Operation(summary = "여행 계획 생성", description = "여행 계획 기본 정보와 일자별 상세 일정을 생성합니다.")
    @PostMapping("/plans")
    public ResponseEntity<Integer> addPlans(@RequestBody PlanCreateRequest request) {
        Integer planId = attractionService.createPlan(request);
        return ResponseEntity.ok(planId);
    }

    @Operation(summary = "여행 계획 목록 조회", description = "회원 아이디로 등록된 여행 계획 목록을 조회합니다.")
    @GetMapping("/plans")
    public ResponseEntity<List<PlanSearchResponse>> searchPlanList(
            @Parameter(description = "여행 계획을 조회할 회원 ID", example = "ssafy01")
            @RequestParam String memberId) {
        List<PlanSearchResponse> plans = attractionService.searchPlanList(memberId);
        return ResponseEntity.ok(plans);
    }

    @Operation(summary = "여행 계획 상세 조회", description = "계획 ID로 일자별 상세 일정을 조회합니다.")
    @GetMapping("/plans/{planId}")
    public ResponseEntity<List<PlanDetailResponse>> searchPlanDetails(
            @Parameter(description = "상세 조회할 여행 계획 ID", example = "1")
            @PathVariable Integer planId) {
        List<PlanDetailResponse> details = attractionService.searchPlanDetails(planId);
        return ResponseEntity.ok(details);
    }

    @Operation(summary = "여행 취향 설문 조회", description = "여행 취향 설문 문항 전체를 조회합니다.")
    @GetMapping("/surveys")
    public ResponseEntity<List<SurveyResponse>> getSurveys() {
        List<SurveyResponse> surveys = attractionService.findAllSurveys();
        return ResponseEntity.ok(surveys);
    }

    @Operation(summary = "여행 계획 삭제", description = "여행 계획과 관련된 상세 일정을 모두 삭제합니다.")
    @DeleteMapping("/plans/{planId}")
    public ResponseEntity<Integer> deletePlan(
            @Parameter(description = "삭제할 여행 계획 ID", example = "1")
            @PathVariable Integer planId) {
        attractionService.deletePlan(planId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "여행 계획 수정", description = "여행 계획 기본 정보와 일정을 수정합니다.")
    @PutMapping("/plans/{planId}")
    public ResponseEntity<Void> updatePlan(
            @Parameter(description = "수정할 여행 계획 ID", example = "1")
            @PathVariable Integer planId,
            @RequestBody PlanUpdateRequest request) {
        attractionService.updatePlan(planId, request);
        return ResponseEntity.noContent().build();
    }
}
