package com.ssafy.nayeogi.story.model.dao;


import com.ssafy.nayeogi.story.model.dto.StoryDetailResponse;
import com.ssafy.nayeogi.story.model.dto.StoryListResponse;
import com.ssafy.nayeogi.story.model.dto.StoryPageDto;
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
    int insertStorybook(StorySaveRequest request);

    /**
     * 스토리 페이지들 일괄 저장
     * @param storyId 생성된 스토리북 ID
     * @param pages 페이지 리스트
     */
    int insertStoryPages(@Param("storyId") int storyId, 
                         @Param("pages") List<StoryPageDto> pages);

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
     * 스토리북 수정 (제목, 썸네일, 공개여부 등)
     */
    int updateStorybook(@Param("storyId") int storyId, @Param("request") StoryUpdateRequest request);

    /**
     * 스토리북에 딸린 모든 페이지 삭제 (수정 시 기존 페이지 날리기용)
     */
    int deleteStoryPages(int storyId);

    /**
     * 스토리북 삭제 (Cascade 설정으로 페이지도 같이 삭제됨)
     */
    int deleteStorybook(int storyId);

    /**
     * 스토리북 작성자 ID 조회 (권한 체크용)
     */
    String selectMemberIdByStoryId(int storyId);
    
    /**
     * 스토리북 공개 여부만 변경
     */
    int updateStoryVisibility(@Param("storyId") int storyId, @Param("isPublic") boolean isPublic);
}
