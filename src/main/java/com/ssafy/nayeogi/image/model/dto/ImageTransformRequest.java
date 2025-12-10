package com.ssafy.nayeogi.image.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(description = "AI 이미지 스타일 변환 요청")
public class ImageTransformRequest {
    
    @Schema(description = "원본 이미지 URL", example = "https://s3.../photo.jpg")
    private String originalUrl;
    
    @Schema(description = "변환할 스타일 (ANIME, SKETCH 등)", example = "ANIME")
    private String style;
}