package com.ssafy.nayeogi.story.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "스토리북 목록 응답 DTO")
public class StoryListResponse {
    
    @Schema(description = "스토리북 ID", example = "501")
    private int storyId;        // storybooks.id
    
    @Schema(description = "여행 계획 ID", example = "101")
    private int planId;         // storybooks.plan_id
    
    @Schema(description = "제목", example = "나의 부산 여행")
    private String title;       // storybooks.title
    
    @Schema(description = "생성일 (yyyy-MM-dd)", example = "2025-11-27")
    private String createdDate; // storybooks.created_at (String으로 변환)
    
    @Schema(description = "썸네일 경로", example = "https://s3.../thumb.jpg")
    private String thumbnailPath; // storybooks.thumbnail_path
    
    @Schema(description = "공개 여부", example = "true")
    private boolean isPublic;   // storybooks.is_public ('Y'/'N' -> boolean)

}