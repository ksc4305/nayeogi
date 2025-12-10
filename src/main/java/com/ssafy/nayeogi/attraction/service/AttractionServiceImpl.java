package com.ssafy.nayeogi.attraction.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.nayeogi.attraction.model.dao.AttractionDao;
import com.ssafy.nayeogi.attraction.model.dto.AttractionResponse;
import com.ssafy.nayeogi.attraction.model.dto.AttractionRecommendationRequest;
import com.ssafy.nayeogi.attraction.model.dto.AttractionRecommendationResponse;
import com.ssafy.nayeogi.attraction.model.dto.PlanCreateRequest;
import com.ssafy.nayeogi.attraction.model.dto.PlanDetailRequest;
import com.ssafy.nayeogi.attraction.model.dto.PlanDetailResponse;
import com.ssafy.nayeogi.attraction.model.dto.PlanSearchResponse;
import com.ssafy.nayeogi.attraction.model.dto.PlanUpdateRequest;
import com.ssafy.nayeogi.attraction.model.dto.SurveyResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttractionServiceImpl implements AttractionService {

	private final AttractionDao attractionDao;

	@Override
	public List<AttractionResponse> searchAttractions(String title, Integer contentTypeId) {
		return attractionDao.searchAttractions(title, contentTypeId);
	}

	@Override
	public Integer createPlan(PlanCreateRequest request) {
		attractionDao.insertPlan(request);
		Integer planId = request.getId();

		List<Integer> attractionIds = request.getAttractionIds();
		if (planId != null && attractionIds != null && !attractionIds.isEmpty()) {
			List<PlanDetailRequest> details = createPlanDetails(attractionIds);
			attractionDao.insertPlanDetails(planId, details);
		}

		return planId;
	}

	@Override
	public List<PlanSearchResponse> searchPlanList(String memberId) {
		return attractionDao.findPlansByMember(memberId);
	}

	@Override
	public List<PlanDetailResponse> searchPlanDetails(Integer planId) {
		return attractionDao.findPlanDetailsByPlanId(planId);
	}
	
	@Override
	public int deletePlan(Integer planId) {
		attractionDao.deletePlanDetails(planId);
		return attractionDao.deletePlan(planId);
	}

	@Override
	public void updatePlan(Integer planId, PlanUpdateRequest request) {
		boolean hasPlanFields = request.getTitle() != null
			|| request.getStartDate() != null
			|| request.getEndDate() != null
			|| request.getDescription() != null;

		if (hasPlanFields) {
			attractionDao.updatePlan(planId, request);
		}

		if (request.getAttractionIds() != null) {
			attractionDao.deletePlanDetails(planId);
			List<Integer> attractionIds = request.getAttractionIds();
			if (!attractionIds.isEmpty()) {
				List<PlanDetailRequest> details = createPlanDetails(attractionIds);
				attractionDao.insertPlanDetails(planId, details);
			}
		}
	}

	@Override
	public List<SurveyResponse> findAllSurveys() {
		return attractionDao.findAllSurveys();
	}

	@Override
	public List<AttractionRecommendationResponse> recommendAttractions(AttractionRecommendationRequest request) {
		return attractionDao.recommendAttractions(request.getArea(), request.getSurveyIds());
	}
	
	//plan_details table의 sequence 계산
	//이렇게 구현하면 간단은 하지만 array processing의 장점을 이용하지 못하니 추후 insert into all 구문으로 바꿀것.
	private List<PlanDetailRequest> createPlanDetails(List<Integer> attractionIds) {
		List<PlanDetailRequest> details = new java.util.ArrayList<>();
		for (int i = 0; i < attractionIds.size(); i++) {
			details.add(new PlanDetailRequest(attractionIds.get(i), i + 1));
		}
		return details;
	}


}
