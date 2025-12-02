package com.ssafy.nayeogi.story.model.dao;

import com.ssafy.nayeogi.story.model.dto.StoryCreateRequest;
import com.ssafy.nayeogi.story.model.dto.StoryPreviewResponse;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * 스토리북 DB 접근을 위한 매퍼 인터페이스
 */
@Mapper
public interface StoryDao {
    int createStory(StoryCreateRequest storyCreateRequest);
    // StoryDetailResponse findStoryById(Long storyId);
    List<StoryPreviewResponse> findAllStories();
    // int updateStory(StoryUpdateRequest storyUpdateRequest);
    int deleteStory(Long storyId);
}
