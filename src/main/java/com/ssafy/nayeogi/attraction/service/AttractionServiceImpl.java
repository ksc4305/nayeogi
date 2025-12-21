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
	public List<AttractionResponse> searchAttractions(String title, List<Integer> contentTypeIds) {
		return attractionDao.searchAttractions(title, contentTypeIds);
	}

	@Override
	public Integer createPlan(PlanCreateRequest request) {
		attractionDao.insertPlan(request);
		Integer planId = request.getId();

		List<PlanDetailRequest> planDetails = request.getPlanDetails();
		if (planId != null && planDetails != null && !planDetails.isEmpty()) {
			attractionDao.insertPlanDetails(planId, planDetails);
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

		List<PlanDetailRequest> planDetails = request.getPlanDetails();
		if (planDetails != null) {
			attractionDao.deletePlanDetails(planId);
			if (!planDetails.isEmpty()) {
				attractionDao.insertPlanDetails(planId, planDetails);
			}
		}
	}

	@Override
	public List<SurveyResponse> findAllSurveys() {
		return attractionDao.findAllSurveys();
	}

	//추후 부분범위처리 가능
	@Override
	public List<AttractionRecommendationResponse> recommendAttractions(AttractionRecommendationRequest request) {
		return attractionDao.recommendAttractions(request.getArea(), request.getContentTypeId(), request.getSurveyIds());
	}
	
}
