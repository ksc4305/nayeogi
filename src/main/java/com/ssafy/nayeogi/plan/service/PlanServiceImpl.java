package com.ssafy.nayeogi.plan.service;

import com.ssafy.nayeogi.plan.model.dao.PlanDao;
import com.ssafy.nayeogi.plan.model.dto.PlanCreateRequest;
import com.ssafy.nayeogi.plan.model.dto.PlanDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 여행 계획 관련 비즈니스 로직 처리를 위한 서비스 구현체
 */
@Service
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService {

    private final PlanDao planDao;

    @Override
    public Long create(PlanCreateRequest planCreateRequest) {
        planDao.createPlan(planCreateRequest);
        // 생성된 PK를 반환해야 하지만, DTO에 id 필드가 없으므로 null 반환
        return null;
    }

    @Override
    public PlanDetailResponse findById(Long planId) {
        return planDao.findPlanById(planId);
    }

    // @Override
    // public List<PlanPreview> findAllByUser(String userId) {
    //     return planDao.findAllByUser(userId);
    // }

    @Override
    public void update(PlanDetailResponse planDetail) {
        planDao.updatePlan(planDetail);
    }

    @Override
    public void delete(Long planId) {
        planDao.deletePlan(planId);
    }
}
