package com.ssafy.nayeogi.plan.controller;

import com.ssafy.nayeogi.plan.model.dto.PlanCreateRequest;
import com.ssafy.nayeogi.plan.model.dto.PlanDetailResponse;
import com.ssafy.nayeogi.plan.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/plans")
@RequiredArgsConstructor
public class PlanController {

    private final PlanService planService;

    /**
     * 여행 계획 생성
     */
    @PostMapping
    public ResponseEntity<?> createPlan(@RequestBody PlanCreateRequest createRequest) {
        Long planId = planService.create(createRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(planId);
    }

    /**
     * 특정 여행 계획 조회
     */
    @GetMapping("/{planId}")
    public ResponseEntity<PlanDetailResponse> getPlan(@PathVariable Long planId) {
        PlanDetailResponse plan = planService.findById(planId);
        if (plan != null) {
            return ResponseEntity.ok(plan);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * 특정 사용자의 모든 여행 계획 조회
     */
    @GetMapping
    public ResponseEntity<?> getAllPlansByUser(@RequestParam String userId) {
        // List<PlanPreview> plans = planService.findAllByUser(userId);
        // return ResponseEntity.ok(plans);
        return ResponseEntity.ok("사용자의 모든 계획 조회 (구현 필요)");
    }

    /**
     * 여행 계획 수정
     */
    @PutMapping("/{planId}")
    public ResponseEntity<?> updatePlan(@PathVariable Long planId, @RequestBody PlanDetailResponse planDetail) {
        // planId와 planDetail의 ID가 일치하는지 검증하는 로직이 필요합니다.
        planService.update(planDetail);
        return ResponseEntity.ok().build();
    }

    /**
     * 여행 계획 삭제
     */
    @DeleteMapping("/{planId}")
    public ResponseEntity<?> deletePlan(@PathVariable Long planId) {
        planService.delete(planId);
        return ResponseEntity.noContent().build();
    }
}
