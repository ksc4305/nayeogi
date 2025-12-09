package com.ssafy.nayeogi.story.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@Schema(description = "스토리북 상세 조회 응답 DTO")
public class StoryDetailResponse {
    
    @Schema(description = "스토리북 ID", example = "501")
    private int storyId;
    
    @Schema(description = "여행 계획 ID", example = "101")
    private int planId;
    
    @Schema(description = "제목", example = "나의 부산 여행")
    private String title;
    
    @Schema(description = "작성자 ID", example = "ssafy01")
    private String memberId;
    
    @Schema(description = "생성일", example = "2025-11-27")
    private String createdDate;
    
    @Schema(description = "공개 여부", example = "true")
    private boolean isPublic;
    
    @Schema(description = "페이지 목록")
    private List<StoryPageDetail> pages;

    
}