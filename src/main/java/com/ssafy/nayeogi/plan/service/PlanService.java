package com.ssafy.nayeogi.plan.service;

import com.ssafy.nayeogi.plan.model.dto.PlanCreateRequest;
import com.ssafy.nayeogi.plan.model.dto.PlanDetailResponse;

import java.util.List;

/**
 * 여행 계획 관련 비즈니스 로직 처리를 위한 서비스 인터페이스
 */
public interface PlanService {

    /**
     * 여행 계획 생성
     * @param planCreateRequest 생성할 계획 정보
     * @return 생성된 계획 ID
     */
    Long create(PlanCreateRequest planCreateRequest);

    /**
     * 특정 여행 계획 상세 조회
     * @param planId 계획 ID
     * @return 계획 상세 정보
     */
    PlanDetailResponse findById(Long planId);

    /**
     * 특정 사용자의 모든 여행 계획 목록 조회
     * @param userId 사용자 ID
     * @return 해당 사용자의 모든 계획 목록
     */
    // List<PlanPreview> findAllByUser(String userId);

    /**
     * 여행 계획 수정
     * @param planDetail 수정할 계획 정보
     */
    void update(PlanDetailResponse planDetail);

    /**
     * 여행 계획 삭제
     * @param planId 삭제할 계획 ID
     */
    void delete(Long planId);
}
