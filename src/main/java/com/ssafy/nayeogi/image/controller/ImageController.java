package com.ssafy.nayeogi.image.controller;

import com.ssafy.nayeogi.common.dto.ApiResponse;
import com.ssafy.nayeogi.image.dto.ImageUploadResponse;
import com.ssafy.nayeogi.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    /**
     * 이미지 파일 업로드
     * 에러 처리는 GlobalExceptionHandler에게 위임.
     */
    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<ImageUploadResponse>> uploadFiles(
            @RequestPart("files") List<MultipartFile> files
    ) {
        // 1. 서비스 호출 (실패하면 서비스에서 throw -> 핸들러가 잡음)
        List<String> urls = imageService.upload(files);
        
        // 2. DTO 생성
        ImageUploadResponse responseDto = new ImageUploadResponse(urls);
        
        // 3. ApiResponse.success()로 감싸서 리턴
        return ResponseEntity.ok(ApiResponse.success(responseDto));
    }
    
    /**
     * 이미지 파일 삭제
     */
    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> deleteFile(@RequestParam("imageUrl") String imageUrl) {
        imageService.delete(imageUrl);
        
        return ResponseEntity.ok(ApiResponse.success());
    }
}