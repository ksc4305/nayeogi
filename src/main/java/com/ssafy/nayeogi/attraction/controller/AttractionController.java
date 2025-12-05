package com.ssafy.nayeogi.attraction.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.nayeogi.attraction.model.dto.AttractionResponse;
import com.ssafy.nayeogi.attraction.service.AttractionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/attractions")
@RequiredArgsConstructor
public class AttractionController {

	private final AttractionService attractionService;

	@GetMapping("/{title}")
	public ResponseEntity<List<AttractionResponse>> searchByTitle(@PathVariable String title) {
		List<AttractionResponse> attractions = attractionService.findByTitle(title);
		return ResponseEntity.ok(attractions);
	}
}
