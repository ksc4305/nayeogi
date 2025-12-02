package com.ssafy.nayeogi.story.controller;

import com.ssafy.nayeogi.story.model.dto.StoryCreateRequest;
import com.ssafy.nayeogi.story.model.dto.StoryPreviewResponse;
import com.ssafy.nayeogi.story.service.StoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stories")
@RequiredArgsConstructor
public class StoryController {

    private final StoryService storyService;

    /**
     * 스토리 생성
     */
    @PostMapping
    public ResponseEntity<?> createStory(@RequestBody StoryCreateRequest createRequest) {
        Long storyId = storyService.create(createRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(storyId);
    }

    /**
     * 모든 스토리 목록 조회
     */
    @GetMapping
    public ResponseEntity<List<StoryPreviewResponse>> getAllStories() {
        List<StoryPreviewResponse> stories = storyService.findAll();
        return ResponseEntity.ok(stories);
    }

    /**
     * 특정 스토리 조회
     */
    @GetMapping("/{storyId}")
    public ResponseEntity<?> getStory(@PathVariable Long storyId) {
        // StoryDetailResponse story = storyService.findById(storyId);
        // return ResponseEntity.ok(story);
        return ResponseEntity.ok("스토리 상세 조회 (구현 필요)");
    }

    /**
     * 스토리 수정
     */
    @PutMapping("/{storyId}")
    public ResponseEntity<?> updateStory(@PathVariable Long storyId, @RequestBody Object updateRequest) {
        // storyService.update(updateRequest);
        return ResponseEntity.ok("스토리 수정 (구현 필요)");
    }

    /**
     * 스토리 삭제
     */
    @DeleteMapping("/{storyId}")
    public ResponseEntity<?> deleteStory(@PathVariable Long storyId) {
        storyService.delete(storyId);
        return ResponseEntity.noContent().build();
    }
}
