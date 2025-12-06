package com.ssafy.nayeogi.image.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class ImageUploadResponse {
    private List<String> imageUrls;
}