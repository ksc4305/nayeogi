package com.ssafy.nayeogi.story.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@Schema(description = "AI 스토리 초안 생성 요청 DTO")
public class StoryPreviewRequest {

    @Schema(description = "여행 계획 ID", example = "101")
    private int planId;

    @Schema(description = "문체 스타일 (EMOTIONAL, FUNNY, FACTUAL 등)", example = "EMOTIONAL")
    private String style;

    @Schema(description = "스토리 항목 리스트")
    private List<PreviewItem> items;

    @Data
    @NoArgsConstructor
    public static class PreviewItem {
        @Schema(description = "관광지 ID", example = "12540")
        private int contentId;

        @Schema(description = "사용자 메모 (AI가 참고할 내용)", example = "바다가 정말 예뻤다.")
        private String userMemo;

        @Schema(description = "이미지 URL 목록", example = "[\"https://s3.../img.jpg\"]")
        private List<String> imageUrls;
    }
}