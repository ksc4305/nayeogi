package com.ssafy.nayeogi.image.controller;

import com.ssafy.nayeogi.image.model.dto.ImageUploadResponse;
import com.ssafy.nayeogi.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFiles(@RequestPart("files") List<MultipartFile> files) {
        List<String> urls = imageService.upload(files);
        
        // 공통 응답 포맷 { "data": { ... } } 적용
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("imageUrls", urls);
        
        Map<String, Object> response = new HashMap<>();
        response.put("data", responseData);

        return ResponseEntity.ok(response);
    }
}