package com.ssafy.nayeogi.story.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@Schema(description = "스토리북 저장 요청 DTO")
public class StorySaveRequest {
    
	@Schema(hidden = true) // DB에서 생성되므로 입력받지 않음
    private int id;
    
    @Schema(description = "여행 계획 ID", example = "101")
    private int planId;
    
    @Schema(description = "스토리북 제목", example = "나의 부산 여행 (감성 Ver)")
    private String title;
    
    @Schema(description = "대표 이미지 URL", example = "https://s3.../cover.jpg")
    private String thumbnailPath;
    
    @Schema(description = "공개 여부", example = "true")
    private boolean isPublic;
    
    @Schema(hidden = true) // 토큰에서 추출하므로 입력받지 않음?
    private String memberId;
    
    @Schema(description = "스토리 페이지 목록")
    private List<StoryPageDto> pages;

}