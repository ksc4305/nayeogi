package com.ssafy.nayeogi.story.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@AllArgsConstructor
@Schema(description = "스토리북 저장 응답")
public class StorySaveResponse {
 
 @Schema(description = "생성된 스토리북 ID", example = "501")
 private int storyId;
}