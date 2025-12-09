package com.ssafy.nayeogi.story.controller;

import com.ssafy.nayeogi.common.dto.ApiResponse;
import com.ssafy.nayeogi.story.model.dto.StoryDetailResponse;
import com.ssafy.nayeogi.story.model.dto.StoryListResponse;
import com.ssafy.nayeogi.story.model.dto.StorySaveRequest;
import com.ssafy.nayeogi.story.model.dto.StoryUpdateRequest;
import com.ssafy.nayeogi.story.model.dto.StoryVisibilityRequest;
import com.ssafy.nayeogi.story.service.StoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "Story API", description = "스토리북 관련 API")
@Slf4j
@RestController
@RequestMapping("/api/v1/stories")
@RequiredArgsConstructor
public class StoryController {

    private final StoryService storyService;

    @Operation(summary = "스토리북 저장", description = "완성된 이야기와 페이지들을 DB에 저장합니다.")
    @PostMapping
    public ResponseEntity<ApiResponse<Map<String, Integer>>> saveStory(
    		@RequestBody StorySaveRequest request,
    		@AuthenticationPrincipal UserDetails userDetails // 시큐리티 인증 객체
    		) {
        
        // TODO: 로그인 기능 구현 후, SecurityContextHolder 등에서 memberId 추출
    	String memberId = (userDetails != null) ? userDetails.getUsername() : "ssafy01";        
        
    	log.info("Story Save Request - User: {}", memberId);
    	
    	int storyId = storyService.saveStory(request, memberId);
        
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("스토리북이 저장되었습니다.", Map.of("storyId", storyId)));
    }
    
    
    @Operation(summary = "내 스토리북 목록 조회", description = "내가 작성한 스토리북 리스트를 조회합니다.")
    @GetMapping
    public ResponseEntity<ApiResponse<Map<String, Object>>> getStoryList(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(required = false) Integer planId // 쿼리 파라미터 (?planId=101)
    ) {
        String memberId = (userDetails != null) ? userDetails.getUsername() : "ssafy01";
        
        List<StoryListResponse> list = storyService.getStoryList(memberId, planId);
        
        return ResponseEntity.ok(ApiResponse.success(Map.of("stories", list)));
    }
    
    @Operation(summary = "스토리북 상세 조회", description = "스토리북의 상세 내용과 페이지들을 조회합니다.")
    @GetMapping("/{storyId}")
    public ResponseEntity<ApiResponse<StoryDetailResponse>> getStoryDetail(
            @PathVariable int storyId,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        // 로그인 안 했으면 null (비공개 글 조회 시 튕겨내기 위함)
        String memberId = (userDetails != null) ? userDetails.getUsername() : null;
        
        StoryDetailResponse response = storyService.getStoryDetail(storyId, memberId);
        
        return ResponseEntity.ok(ApiResponse.success(response));
    }
    
    @Operation(summary = "스토리북 수정", description = "기존 스토리북의 내용을 수정합니다.")
    @PutMapping("/{storyId}")
    public ResponseEntity<ApiResponse<Void>> modifyStory(
            @PathVariable int storyId,
            @RequestBody StoryUpdateRequest request,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        String memberId = (userDetails != null) ? userDetails.getUsername() : "ssafy01";
        
        storyService.modifyStory(storyId, request, memberId);
        
        return ResponseEntity.ok(ApiResponse.success("스토리북이 수정되었습니다."));
    }

    @Operation(summary = "스토리북 삭제", description = "스토리북을 영구 삭제합니다.")
    @DeleteMapping("/{storyId}")
    public ResponseEntity<ApiResponse<Void>> deleteStory(
            @PathVariable int storyId,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        String memberId = (userDetails != null) ? userDetails.getUsername() : "ssafy01";
        
        storyService.deleteStory(storyId, memberId);
        
        return ResponseEntity.ok(ApiResponse.success("스토리북이 삭제되었습니다."));
    }
    
    @Operation(summary = "스토리북 공개 여부 변경", description = "스토리북의 공개/비공개 상태를 변경합니다.")
    @PatchMapping("/{storyId}/visibility")
    public ResponseEntity<ApiResponse<Void>> changeVisibility(
            @PathVariable int storyId,
            @RequestBody StoryVisibilityRequest request,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        String memberId = (userDetails != null) ? userDetails.getUsername() : "ssafy01";
        
        storyService.changeVisibility(storyId, request.isPublic(), memberId);
        
        return ResponseEntity.ok(ApiResponse.success("공개 여부가 변경되었습니다."));
    }
}