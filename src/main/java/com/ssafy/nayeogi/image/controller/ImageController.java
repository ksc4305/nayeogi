package com.ssafy.nayeogi.image.controller;

import com.ssafy.nayeogi.common.dto.ApiResponse;
import com.ssafy.nayeogi.image.model.dto.ImageTransformRequest;
import com.ssafy.nayeogi.image.model.dto.ImageTransformResponse;
import com.ssafy.nayeogi.image.model.dto.ImageUploadResponse;
import com.ssafy.nayeogi.image.service.ImageService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Slf4j
@Tag(name = "Image API", description = "이미지 업로드, 삭제 및 AI 스타일 변환 API")
@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    /**
     * 이미지 파일 업로드
     * Swagger에서 파일 업로드를 테스트하려면 consumes 타입을 명시해야함.
     */
    @Operation(summary = "이미지 파일 업로드", description = "로컬 이미지 파일을 S3에 업로드하고 URL을 반환합니다.")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<ImageUploadResponse>> uploadFiles(
            @Parameter(description = "업로드할 이미지 파일 목록") 
            @RequestPart("files") List<MultipartFile> files
    ) {
        List<String> urls = imageService.upload(files);
        ImageUploadResponse responseDto = new ImageUploadResponse(urls);
        
        return ResponseEntity.ok(ApiResponse.success(responseDto));
    }
    
    /**
     * 이미지 파일 삭제
     */
    @Operation(summary = "이미지 파일 삭제", description = "S3에 저장된 이미지를 URL을 통해 삭제합니다.")
    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> deleteFile(
            @Parameter(description = "삭제할 이미지의 전체 URL", example = "https://s3.../uuid.jpg") 
            @RequestParam("imageUrl") String imageUrl
    ) {
        imageService.delete(imageUrl);
        
        return ResponseEntity.ok(ApiResponse.success("이미지가 삭제되었습니다."));
    }
    
    /**
     * AI 이미지 스타일 변환
     */
    @Operation(summary = "AI 이미지 스타일 변환", description = "업로드된 사진을 지정된 화풍(ANIME, SKETCH 등)으로 변환합니다.")
    @PostMapping("/transformations")
    public ResponseEntity<ApiResponse<ImageTransformResponse>> transformImage(
            @RequestBody ImageTransformRequest request
    ) {
        // 실제로는 여기서 AI 서버 통신 로직이 수행됨
        String newUrl = imageService.transformImage(request.getOriginalUrl(), request.getStyle());
        
        return ResponseEntity.ok(ApiResponse.success(
                "이미지 변환이 완료되었습니다.", 
                new ImageTransformResponse(newUrl)
        ));
    }
}