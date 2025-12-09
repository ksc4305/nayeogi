package com.ssafy.nayeogi.story.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(description = "스토리북 공개 여부 변경 요청 DTO")
public class StoryVisibilityRequest {

    @Schema(description = "변경할 공개 여부 (true: 공개, false: 비공개)", example = "true")
    private boolean isPublic;
}