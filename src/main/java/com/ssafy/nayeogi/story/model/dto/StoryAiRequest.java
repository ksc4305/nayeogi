package com.ssafy.nayeogi.story.model.dto;

import lombok.Data;
import java.util.List;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Schema(description = "AI 스토리 생성 요청 DTO")
public class StoryAiRequest {

    @Schema(description = "스토리 제목", example = "우정 가득 제주도 3박 4일 여행")
    private String storyTitle;

    @Schema(description = "여행 시작일", example = "2024-05-10")
    private String startDate;

    @Schema(description = "여행 종료일", example = "2024-05-13")
    private String endDate;

    @Schema(description = "동반자 목록", example = "[\"friends\"]")
    private List<String> companions;

    @Schema(description = "스토리 톤 목록", example = "[\"humorous\", \"calm\"]")
    private List<String> tones;

    @Schema(description = "여행 일자별 상세 정보")
    private List<DayDto> storyDays;

    @Data
    @Schema(description = "여행 일자별 상세 정보 DTO")
    public static class DayDto {
        @Schema(description = "몇 번째 날", example = "1")
        private int dayNum;

        @Schema(description = "해당 날짜", example = "2024-05-10")
        private String date;

        @Schema(description = "해당 날짜 날씨", example = "[\"sunny\", \"windy\"]")
        private List<String> weather;

        @Schema(description = "해당 날짜의 섹션(장소) 정보")
        private List<SectionDto> sections;
    }

    @Data
    @Schema(description = "여행 일자 내 개별 장소 섹션 정보 DTO")
    public static class SectionDto {
        @Schema(description = "장소 이름", example = "제주국제공항")
        private String placeName;

        @Schema(description = "방문 순서", example = "1")
        private int visitOrder;

        @Schema(description = "사용자 메모", example = "여행의 시작! 공항에 내리자마자 바람이 엄청 불었다.")
        private String content;

        @Schema(description = "이미지 URL 목록", example = "[\"https://.../image1.png\"]")
        private List<String> imageUrls;

        @Schema(description = "선택된 태그 목록 (장소 분위기)", example = "[\"공항\", \"바람 많음\"]")
        private List<String> selectedTags;
    }
}