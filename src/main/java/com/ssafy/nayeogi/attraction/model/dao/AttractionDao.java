package com.ssafy.nayeogi.attraction.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.nayeogi.attraction.model.dto.AttractionResponse;
import com.ssafy.nayeogi.attraction.model.dto.PlanCreateRequest;
import com.ssafy.nayeogi.attraction.model.dto.PlanDetailRequest;

@Mapper
public interface AttractionDao {

	List<AttractionResponse> searchAttractions(String title,Integer contentTypeId);

	int insertPlan(PlanCreateRequest request);

	int insertPlanDetails(Integer planId, List<PlanDetailRequest> details);
}
