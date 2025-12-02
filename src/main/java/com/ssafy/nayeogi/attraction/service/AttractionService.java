package com.ssafy.nayeogi.attraction.service;

import com.ssafy.nayeogi.attraction.model.dto.AttractionSearchCondition;
import java.util.List;

/**
 * 관광지 관련 비즈니스 로직 처리를 위한 서비스 인터페이스
 */
public interface AttractionService {

    /**
     * 조건에 맞는 관광지 목록 조회
     * @param searchCondition 검색 조건
     * @return 관광지 목록
     */
    // List<AttractionInfo> search(AttractionSearchCondition searchCondition);

    /**
     * 특정 관광지 상세 정보 조회
     * @param contentId 관광지 ID
     * @return 관광지 상세 정보
     */
    // AttractionInfo findById(int contentId);
}
