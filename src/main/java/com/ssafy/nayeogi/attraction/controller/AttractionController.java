package com.ssafy.nayeogi.attraction.controller;

import com.ssafy.nayeogi.attraction.model.dto.AttractionSearchCondition;
import com.ssafy.nayeogi.attraction.service.AttractionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 관광지 정보 관련 API 요청을 처리하는 컨트롤러
 */
@RestController
@RequestMapping("/attractions")
@RequiredArgsConstructor
public class AttractionController {

    private final AttractionService attractionService;

    /**
     * 관광지 검색
     * @param searchCondition 검색 조건 (e.g., /attractions?areaCode=1&contentTypeId=12&keyword=서울)
     * @return 검색된 관광지 목록
     */
    @GetMapping
    public ResponseEntity<?> searchAttractions(AttractionSearchCondition searchCondition) {
        // List<AttractionInfo> attractions = attractionService.search(searchCondition);
        // return ResponseEntity.ok(attractions);
        return ResponseEntity.ok("관광지 목록 조회 (구현 필요)");
    }

    /**
     * 관광지 상세 정보 조회
     * @param contentId 관광지 ID
     * @return 관광지 상세 정보
     */
    @GetMapping("/{contentId}")
    public ResponseEntity<?> getAttractionDetail(@PathVariable int contentId) {
        // AttractionInfo attraction = attractionService.findById(contentId);
        // if (attraction != null) {
        //     return ResponseEntity.ok(attraction);
        // }
        // return ResponseEntity.notFound().build();
        return ResponseEntity.ok("관광지 상세 정보 조회 (구현 필요)");
    }
}
