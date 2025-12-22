package com.ssafy.nayeogi.story.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@Schema(description = "스토리 수정 요청 DTO")
public class StoryUpdateRequest {
    
    @Schema(description = "수정할 제목", example = "나의 부산 여행 (수정본)")
    private String title;
    
    @Schema(description = "본문" , example = "부산 어묵은 진짜 맛있었다..")
    private String content;
    
    @Schema(description = "수정할 썸네일 경로", example = "https://s3.../new_thumb.jpg")
    private String thumbnailPath;
    
    @Schema(description = "공개 여부", example = "true")
    private boolean isPublic;
    
}