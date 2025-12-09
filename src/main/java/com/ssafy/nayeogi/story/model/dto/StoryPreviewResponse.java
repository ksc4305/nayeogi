package com.ssafy.nayeogi.story.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "AI 스토리 초안 응답 DTO")
public class StoryPreviewResponse {

    @Schema(description = "생성된 스토리 페이지들")
    private List<GeneratedPage> pages;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GeneratedPage {
        @Schema(description = "관광지 ID", example = "12540")
        private int contentId;

        @Schema(description = "장소명 (관광지 이름)", example = "해운대 해수욕장")
        private String title;

        @Schema(description = "AI가 생성한 텍스트", example = "파도 소리가 감미롭게 들려오는 해운대의 밤은...")
        private String aiText;
    }
}