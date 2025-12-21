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
    
    @Schema(description = "본문" , example = "부산 어묵은 진짜 맛있었다..")
    private String content;
    
    @Schema(description = "작성자 ID", example = "ssafy01")
    private String memberId;
    
    @Schema(description = "작성자 이름", example = "김싸피")
    private String writerName;
    
    
    @Schema(description = "생성일", example = "2025-11-27")
    private String createdDate;
    
    @Schema(description = "썸네일 경로", example = "https://s3.../thumb.jpg")
    private String thumbnailPath;
    
    @Schema(description = "공개 여부", example = "true")
    private boolean isPublic;


    
}