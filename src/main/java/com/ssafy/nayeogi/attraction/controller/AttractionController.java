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

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AttractionController {

	private final AttractionService attractionService;
	
	@PostMapping("/recommendations")
	public ResponseEntity<List<AttractionRecommendationResponse>> recommendAttractions(
		@RequestBody AttractionRecommendationRequest request) {
		List<AttractionRecommendationResponse> recommendations = attractionService.recommendAttractions(request);
		return ResponseEntity.ok(recommendations);
	}

	@GetMapping("/attractions")
	public ResponseEntity<List<AttractionResponse>> searchAttractions(@RequestParam(required = false) String title, 
			@RequestParam(required = false) Integer contentTypeId) {
		List<AttractionResponse> attractions = attractionService.searchAttractions(title, contentTypeId);
		return ResponseEntity.ok(attractions);
	}
	@PostMapping("/plans")
	public ResponseEntity<Integer> addPlans(@RequestBody PlanCreateRequest request) {
		Integer planId = attractionService.createPlan(request);
		return ResponseEntity.ok(planId);
	}

	@GetMapping("/plans")
	public ResponseEntity<List<PlanSearchResponse>> searchPlanList(@RequestParam String memberId) {
		List<PlanSearchResponse> plans = attractionService.searchPlanList(memberId);
		return ResponseEntity.ok(plans);
	}

	@GetMapping("/plans/{planId}")
	public ResponseEntity<List<PlanDetailResponse>> searchPlanDetails(@PathVariable Integer planId) {
		List<PlanDetailResponse> details = attractionService.searchPlanDetails(planId);
		return ResponseEntity.ok(details);
	}
	
	@GetMapping("/surveys")
	public ResponseEntity<List<SurveyResponse>> getSurveys() {
		List<SurveyResponse> surveys = attractionService.findAllSurveys();
		return ResponseEntity.ok(surveys);
	}
	
	@DeleteMapping("/plans/{planId}")
	public ResponseEntity<Integer> deletePlan(@PathVariable Integer planId){
		attractionService.deletePlan(planId);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/plans/{planId}")
	public ResponseEntity<Void> updatePlan(@PathVariable Integer planId, @RequestBody PlanUpdateRequest request) {
		attractionService.updatePlan(planId, request);
		return ResponseEntity.noContent().build();
	}
}
