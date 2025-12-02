package com.ssafy.nayeogi.story.service;

import com.ssafy.nayeogi.story.model.dto.StoryCreateRequest;
import com.ssafy.nayeogi.story.model.dto.StoryPreviewResponse;

import java.util.List;

/**
 * 스토리북 관련 비즈니스 로직 처리를 위한 서비스 인터페이스
 */
public interface StoryService {

    /**
     * 스토리 생성
     * @param createRequest 생성할 스토리 정보
     * @return 생성된 스토리 ID
     */
    Long create(StoryCreateRequest createRequest);

    /**
     * 특정 스토리 상세 조회
     * @param storyId 스토리 ID
     * @return 스토리 상세 정보
     */
    // StoryDetailResponse findById(Long storyId);

    /**
     * 모든 스토리 목록 조회 (미리보기)
     * @return 모든 스토리 목록
     */
    List<StoryPreviewResponse> findAll();

    /**
     * 스토리 수정
     * @param storyUpdateRequest 수정할 스토리 정보
     */
    // void update(StoryUpdateRequest storyUpdateRequest);

    /**
     * 스토리 삭제
     * @param storyId 삭제할 스토리 ID
     */
    void delete(Long storyId);
}
