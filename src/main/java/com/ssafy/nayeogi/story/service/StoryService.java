package com.ssafy.nayeogi.story.service;

import java.util.List;

import com.ssafy.nayeogi.story.model.dto.StoryAIRequest;
import com.ssafy.nayeogi.story.model.dto.StoryDetailResponse;
import com.ssafy.nayeogi.story.model.dto.StoryListResponse;
import com.ssafy.nayeogi.story.model.dto.StoryPlanDetailResponse;
import com.ssafy.nayeogi.story.model.dto.StoryPreviewRequest;
import com.ssafy.nayeogi.story.model.dto.StoryPreviewResponse;
import com.ssafy.nayeogi.story.model.dto.StorySaveRequest;
import com.ssafy.nayeogi.story.model.dto.StoryUpdateRequest;

public interface StoryService {
    /**
     * 스토리 저장
     */
	int generateAndSaveStory(StoryAIRequest request, String memberId);
    /**
     * 스토리 목록 조회(전체 or 특정 여행의 스토리)
     */
    List<StoryListResponse> getStoryList(String memberId, Integer planId);
    /**
     * 상세 스토리 조회
     */
    StoryDetailResponse getStoryDetail(int storyId, String memberId);
    /**
     * 스토리 수정
     */
    void modifyStory(int storyId, StoryUpdateRequest request, String memberId);
    /**
     * 스토리 삭제
     */
    void deleteStory(int storyId, String memberId);
    /**
     * 공개 범위 설정
     */
    void changeVisibility(int storyId, boolean isPublic, String memberId);
    /**
     * AI 스토리 초안 생성
     */
    StoryPreviewResponse generateStoryPreview(StoryPreviewRequest request);

    StoryPlanDetailResponse getPlanDetailForStory(int planId);
}