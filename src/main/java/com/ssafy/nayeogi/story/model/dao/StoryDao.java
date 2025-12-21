package com.ssafy.nayeogi.story.model.dao;


import com.ssafy.nayeogi.story.model.dto.StoryDetailResponse;
import com.ssafy.nayeogi.story.model.dto.StoryListResponse;
import com.ssafy.nayeogi.story.model.dto.StoryPlanDetailResponse;
import com.ssafy.nayeogi.story.model.dto.StorySaveRequest;
import com.ssafy.nayeogi.story.model.dto.StoryUpdateRequest;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface StoryDao {

    /**
     * 스토리북 메인 정보 저장
     * @param request 스토리북 정보 (id는 DB 저장 후 채워짐)
     */
    int insertStory(StorySaveRequest request);

    /**
     * 스토리북 수정 (제목, 썸네일, 공개여부 등)
     */
    int updateStory(@Param("storyId") int storyId, @Param("request") StoryUpdateRequest request);

    /**
     * 스토리 삭제
     */
    int deleteStory(int storyId);

    /**
     * 스토리북 작성자 ID 조회 (권한 체크용)
     */
    String selectMemberIdByStoryId(int storyId);
    /**
     * 내 스토리북 목록 조회
     * @param memberId 사용자 ID
     * @param planId 여행 계획 ID (없으면 0 또는 null)
     * @return 스토리북 리스트
     */
    List<StoryListResponse> selectStoryList(@Param("memberId") String memberId, 
    		@Param("planId") Integer planId);
    
    /**
     * 스토리북 상세 조회
     * @param storyId 조회할 스토리 ID
     * @return 스토리북 상세 정보 (페이지 포함)
     */
    StoryDetailResponse selectStoryDetail(int storyId);
    
    
    /**
     * 스토리북 공개 여부만 변경
     */
    int updateStoryVisibility(@Param("storyId") int storyId, @Param("isPublic") boolean isPublic);

 // 스토리 작성을 위한 계획 상세 정보 조회
    StoryPlanDetailResponse selectPlanDetailForStory(int planId);
}

