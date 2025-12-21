package com.ssafy.nayeogi.story.model.dto;

import lombok.Data;
import java.util.List;

@Data
public class StoryAIRequest {
    // 1. 필드명 통일
    private String storyTitle;

    // 2. 여러 값을 받을 수 있도록 List<String>으로 변경
    private List<String> companions;
     private List<String> tones;

     // 3. 더 정확한 날짜 정보 수신
     private String startDate;
     private String endDate;

     // 4. 필드명 통일
     private List<StoryDayDto> storyDays;

     @Data
     public static class StoryDayDto {
         private int dayNum;
         private String date;

         // 5. 날씨를 Day 레벨로 이동, List<String>으로 변경
         private List<String> weather;

         private List<StorySectionDto> sections;
     }

     @Data
     public static class StorySectionDto {
         private String placeName;

         // 6. 필드명 변경 및 List<String> 유지
         private List<String> atmosphereTags;

         // 7. 필드명 통일
         private String memo;

         private List<String> imageUrls;
     }
 }