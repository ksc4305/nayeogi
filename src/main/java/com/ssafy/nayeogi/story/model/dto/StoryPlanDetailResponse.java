package com.ssafy.nayeogi.story.model.dto;

import lombok.Data;
import java.util.List;

@Data
public class StoryPlanDetailResponse {
    private int id;
    private String title;
    private String startDate;
    private String endDate;
    private String description;
    
    // 일차별 상세 정보 리스트
    private List<DetailDto> details;

    @Data
    public static class DetailDto {
        private int planDate;
        private int sequence;
        private AttractionDto attraction;
    }

    @Data
    public static class AttractionDto {
        private int id;
        private String title;
    }
}