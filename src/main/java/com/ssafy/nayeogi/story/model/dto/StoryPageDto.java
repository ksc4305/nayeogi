package com.ssafy.nayeogi.story.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(description = "스토리 페이지 정보 (저장/수정 공용)")
public class StoryPageDto {
    
    @Schema(description = "관광지 ID", example = "12540")
    private int contentId;
    
    @Schema(description = "이미지 경로", example = "https://s3.../img.jpg")
    private String imagePath;
    
    @Schema(description = "본문 내용", example = "내용입니다.")
    private String content; 

}