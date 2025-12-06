package com.ssafy.nayeogi.attraction.service;

import java.util.List;

import com.ssafy.nayeogi.attraction.model.dto.AttractionResponse;
import com.ssafy.nayeogi.attraction.model.dto.PlanCreateRequest;

public interface AttractionService {

	List<AttractionResponse> searchAttractions(String title, Integer contentTypeId);

	Integer createPlan(PlanCreateRequest request);
}
