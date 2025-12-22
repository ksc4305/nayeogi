package com.ssafy.nayeogi.story.controller;

import com.ssafy.nayeogi.common.dto.ApiResponseDto;
import com.ssafy.nayeogi.member.model.dto.MemberDto;
import com.ssafy.nayeogi.story.model.dto.AiStoryRequest;
import com.ssafy.nayeogi.story.model.dto.StoryDetailResponse;
import com.ssafy.nayeogi.story.model.dto.StoryListResponse;
import com.ssafy.nayeogi.story.model.dto.StoryPlanDetailResponse;
import com.ssafy.nayeogi.story.model.dto.StoryPreviewRequest;
import com.ssafy.nayeogi.story.model.dto.StoryPreviewResponse;
import com.ssafy.nayeogi.story.model.dto.StorySaveRequest;
import com.ssafy.nayeogi.story.model.dto.StorySaveResponse;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Story API", description = "스토리북 관련 API")
@Slf4j
@RestController
@RequestMapping("/api/v1/stories")
@RequiredArgsConstructor
public class StoryController {

    private final StoryService storyService;

    /**
     * AI 여행기 생성 및 자동 저장 (비공개 상태)
     */
    @Operation(summary = "AI 여행기 생성 요청 및 저장", description = "입력된 정보를 바탕으로 AI가 여행기를 작성하고 비공개 상태로 저장합니다.")
    @PostMapping("/ai-generate")
    public ResponseEntity<ApiResponseDto<StorySaveResponse>> generateAIStory(
            @RequestBody AiStoryRequest request,
            @AuthenticationPrincipal MemberDto memberDto // 로그인한 사용자 정보
    ) {
    	String memberId = (memberDto != null) ? memberDto.getUserId() : null;        
        
        // 서비스 호출 -> 생성 후 저장된 ID 반환
        int storyId = storyService.generateAndSaveStory(request, memberId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDto.success("스토리북이 저장되었습니다.", new StorySaveResponse(storyId)));
    }
    
    @Operation(summary = "내 스토리북 목록 조회", description = "내가 작성한 스토리북 리스트를 조회합니다.")
    @GetMapping

    public ResponseEntity<ApiResponseDto<List<StoryListResponse>>> getStoryList(
    		@AuthenticationPrincipal MemberDto memberDto,
            @RequestParam(required = false) Integer planId // 쿼리 파라미터 (?planId=101)
    ) {
    	String memberId = (memberDto != null) ? memberDto.getUserId() : null;        
                
    	List<StoryListResponse> response = storyService.getStoryList(memberId, planId);

        return ResponseEntity.ok(ApiResponseDto.success(response));
    }
    
    @Operation(summary = "스토리북 상세 조회", description = "스토리북의 상세 내용과 페이지들을 조회합니다.")
    @GetMapping("/{storyId}")
    public ResponseEntity<ApiResponseDto<StoryDetailResponse>> getStoryDetail(
            @PathVariable int storyId,
    		@AuthenticationPrincipal MemberDto memberDto
    ) {
        // 로그인 안 했으면 null (비공개 글 조회 시 튕겨내기 위함)
    	String memberId = (memberDto != null) ? memberDto.getUserId() : null;        
        
        StoryDetailResponse response = storyService.getStoryDetail(storyId, memberId);
        
        return ResponseEntity.ok(ApiResponseDto.success(response));
    }
    
    @Operation(summary = "스토리북 수정", description = "기존 스토리북의 내용을 수정합니다.")
    @PutMapping("/{storyId}")
    public ResponseEntity<ApiResponseDto<Void>> modifyStory(
            @PathVariable int storyId,
            @RequestBody StoryUpdateRequest request,
    		@AuthenticationPrincipal MemberDto memberDto
    ) {
    	String memberId = (memberDto != null) ? memberDto.getUserId() : null;        
        
        storyService.modifyStory(storyId, request, memberId);
        
        return ResponseEntity.ok(ApiResponseDto.success("스토리북이 수정되었습니다."));
    }

    @Operation(summary = "스토리북 삭제", description = "스토리북을 영구 삭제합니다.")
    @DeleteMapping("/{storyId}")
    public ResponseEntity<ApiResponseDto<Void>> deleteStory(
            @PathVariable int storyId,
    		@AuthenticationPrincipal MemberDto memberDto
    ) {
    	String memberId = (memberDto != null) ? memberDto.getUserId() : null;        
        
        storyService.deleteStory(storyId, memberId);
        
        return ResponseEntity.ok(ApiResponseDto.success("스토리북이 삭제되었습니다."));
    }
    
    @Operation(summary = "스토리북 공개 여부 변경", description = "스토리북의 공개/비공개 상태를 변경합니다.")
    @PatchMapping("/{storyId}/visibility")
    public ResponseEntity<ApiResponseDto<Void>> changeVisibility(
            @PathVariable int storyId,
            @RequestBody StoryVisibilityRequest request,
    		@AuthenticationPrincipal MemberDto memberDto
    ) {
    	String memberId = (memberDto != null) ? memberDto.getUserId() : null;        
        
        storyService.changeVisibility(storyId, request.isPublic(), memberId);
        
        return ResponseEntity.ok(ApiResponseDto.success("공개 여부가 변경되었습니다."));
    }
    
    
    @Operation(summary = "스토리 작성을 위한 계획 정보 조회", description = "특정 여행 계획의 정보를 스토리 생성 페이지 규격에 맞춰 조회합니다.")
    @GetMapping("/plan-info/{planId}")
    public ResponseEntity<ApiResponseDto<StoryPlanDetailResponse>> getPlanInfoForStory(@PathVariable int planId) {
        StoryPlanDetailResponse response = storyService.getPlanDetailForStory(planId);
        return ResponseEntity.ok(ApiResponseDto.success(response));
    }

}