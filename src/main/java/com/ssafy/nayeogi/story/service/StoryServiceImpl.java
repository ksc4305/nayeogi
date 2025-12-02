package com.ssafy.nayeogi.story.service;

import com.ssafy.nayeogi.story.model.dao.StoryDao;
import com.ssafy.nayeogi.story.model.dto.StoryCreateRequest;
import com.ssafy.nayeogi.story.model.dto.StoryPreviewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 스토리북 관련 비즈니스 로직 처리를 위한 서비스 구현체
 */
@Service
@RequiredArgsConstructor
public class StoryServiceImpl implements StoryService {

    private final StoryDao storyDao;

    @Override
    public Long create(StoryCreateRequest createRequest) {
        storyDao.createStory(createRequest);
        // 생성된 PK 반환 로직 필요
        return null;
    }

    // @Override
    // public StoryDetailResponse findById(Long storyId) {
    //     return storyDao.findStoryById(storyId);
    // }

    @Override
    public List<StoryPreviewResponse> findAll() {
        return storyDao.findAllStories();
    }

    // @Override
    // public void update(StoryUpdateRequest storyUpdateRequest) {
    //     storyDao.updateStory(storyUpdateRequest);
    // }

    @Override
    public void delete(Long storyId) {
        storyDao.deleteStory(storyId);
    }
}
