package com.ssafy.nayeogi.attraction.service;

import java.util.List;

import com.ssafy.nayeogi.attraction.model.dto.AttractionResponse;
import com.ssafy.nayeogi.attraction.model.dto.PlanCreateRequest;
import com.ssafy.nayeogi.attraction.model.dto.PlanDetailResponse;
import com.ssafy.nayeogi.attraction.model.dto.PlanSearchResponse;
import com.ssafy.nayeogi.attraction.model.dto.PlanUpdateRequest;

public interface AttractionService {

	List<AttractionResponse> searchAttractions(String title, Integer contentTypeId);

	Integer createPlan(PlanCreateRequest request);

	List<PlanSearchResponse> searchPlanList(String memberId);

	List<PlanDetailResponse> searchPlanDetails(Integer planId);

	void updatePlan(Integer planId, PlanUpdateRequest request);

}
