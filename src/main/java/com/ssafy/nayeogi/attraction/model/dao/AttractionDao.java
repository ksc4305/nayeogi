package com.ssafy.nayeogi.attraction.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.nayeogi.attraction.model.dto.AttractionResponse;
import com.ssafy.nayeogi.attraction.model.dto.AttractionRecommendationResponse;
import com.ssafy.nayeogi.attraction.model.dto.PlanCreateRequest;
import com.ssafy.nayeogi.attraction.model.dto.PlanDetailRequest;
import com.ssafy.nayeogi.attraction.model.dto.PlanDetailResponse;
import com.ssafy.nayeogi.attraction.model.dto.PlanSearchResponse;
import com.ssafy.nayeogi.attraction.model.dto.PlanUpdateRequest;
import com.ssafy.nayeogi.attraction.model.dto.SurveyResponse;

@Mapper
public interface AttractionDao {

	List<AttractionResponse> searchAttractions(@Param("title") String title,
		@Param("contentTypeId") Integer contentTypeId);

	int insertPlan(PlanCreateRequest request);

	int insertPlanDetails(@Param("planId") Integer planId, @Param("details") List<PlanDetailRequest> details);

	List<PlanDetailResponse> findPlanDetailsByPlanId(@Param("planId") Integer planId);

	List<PlanSearchResponse> findPlansByMember(@Param("memberId") String memberId);

	int updatePlan(@Param("planId") Integer planId, @Param("request") PlanUpdateRequest request);

	int deletePlanDetails(@Param("planId") Integer planId);

	int deletePlan(@Param("planId") Integer planId);

	List<SurveyResponse> findAllSurveys();

	List<AttractionRecommendationResponse> recommendAttractions(@Param("area") String area,
		@Param("surveyIds") List<Integer> surveyIds);
}
