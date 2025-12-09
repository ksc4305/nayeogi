package com.ssafy.nayeogi.story.service;

import java.util.List;

import com.ssafy.nayeogi.story.model.dto.StoryDetailResponse;
import com.ssafy.nayeogi.story.model.dto.StoryListResponse;
import com.ssafy.nayeogi.story.model.dto.StorySaveRequest;
import com.ssafy.nayeogi.story.model.dto.StoryUpdateRequest;

public interface StoryService {
    int saveStory(StorySaveRequest request, String memberId);
    List<StoryListResponse> getStoryList(String memberId, Integer planId);
    StoryDetailResponse getStoryDetail(int storyId, String memberId);
    void modifyStory(int storyId, StoryUpdateRequest request, String memberId);
    void deleteStory(int storyId, String memberId);
    void changeVisibility(int storyId, boolean isPublic, String memberId);

}