package com.ssafy.nayeogi.story.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
public class StorySaveRequest {
    
    private int id;
    private int planId;
    private String title;
    private String thumbnailPath;
    private boolean isPublic;
    private String memberId;
    
    private List<StoryPageRequest> pages;

    @Data
    @NoArgsConstructor
    public static class StoryPageRequest {
        private int contentId;
        private String imagePath;
        private String content;
    }
}