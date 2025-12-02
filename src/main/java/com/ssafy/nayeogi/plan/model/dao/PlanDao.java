package com.ssafy.nayeogi.plan.model.dao;

import com.ssafy.nayeogi.plan.model.dto.PlanCreateRequest;
import com.ssafy.nayeogi.plan.model.dto.PlanDetailResponse;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * 여행 계획 DB 접근을 위한 매퍼 인터페이스
 */
@Mapper
public interface PlanDao {
    int createPlan(PlanCreateRequest planCreateRequest);
    PlanDetailResponse findPlanById(Long planId);
    // List<PlanPreview> findAllByUser(String userId);
    int updatePlan(PlanDetailResponse planDetail); // PlanUpdateRequest가 없으므로 DetailResponse를 사용
    int deletePlan(Long planId);
}
