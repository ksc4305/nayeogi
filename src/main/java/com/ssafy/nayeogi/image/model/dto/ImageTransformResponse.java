package com.ssafy.nayeogi.image.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageTransformResponse {
    private String transformedUrl;
}