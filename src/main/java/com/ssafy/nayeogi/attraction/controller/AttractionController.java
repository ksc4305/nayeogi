package com.ssafy.nayeogi.attraction.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.nayeogi.attraction.model.dto.AttractionResponse;
import com.ssafy.nayeogi.attraction.model.dto.PlanCreateRequest;
import com.ssafy.nayeogi.attraction.model.dto.PlanCreateResponse;
import com.ssafy.nayeogi.attraction.service.AttractionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AttractionController {

	private final AttractionService attractionService;

	@GetMapping("/attractions")
	public ResponseEntity<List<AttractionResponse>> searchAttractions(@RequestParam(required = false) String title, 
			@RequestParam(required = false) Integer contentTypeId) {
		List<AttractionResponse> attractions = attractionService.searchAttractions(title, contentTypeId);
		return ResponseEntity.ok(attractions);
	}
	@PostMapping("/plans")
	public ResponseEntity<PlanCreateResponse> addPlans(@RequestBody PlanCreateRequest request) {
		Integer planId = attractionService.createPlan(request);
		return ResponseEntity.ok(new PlanCreateResponse(planId));
	}
}
