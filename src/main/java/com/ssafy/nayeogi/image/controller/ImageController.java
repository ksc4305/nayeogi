package com.ssafy.nayeogi.image.controller;

import com.ssafy.nayeogi.common.dto.ErrorResponse;
import com.ssafy.nayeogi.image.model.dto.ImageUploadResponse;
import com.ssafy.nayeogi.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
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

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFiles(@RequestPart("files") List<MultipartFile> files) {
	     try {
	         List<String> urls = imageService.upload(files);
	         return ResponseEntity.ok(new ImageUploadResponse(urls));
	
	     } catch (Exception e) {
	         log.error("파일 업로드 실패: ", e);
	
	         ErrorResponse errorResponse = new ErrorResponse(
	             "FILE_UPLOAD_ERROR", 
	             "파일 업로드 중 오류가 발생했습니다: " + e.getMessage()
	         );
	
	         return ResponseEntity
	                 .status(HttpStatus.INTERNAL_SERVER_ERROR)
	                 .body(errorResponse);
	     }
    }
    
    @DeleteMapping
    public ResponseEntity<?> deleteFile(@RequestParam("imageUrl") String imageUrl) {
        try {
            imageService.delete(imageUrl);
            
            return ResponseEntity.ok().build(); 

        } catch (Exception e) {
            log.error("파일 삭제 실패: ", e);

            ErrorResponse errorResponse = new ErrorResponse(
                "FILE_DELETE_ERROR",
                "파일 삭제 중 오류가 발생했습니다: " + e.getMessage()
            );

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(errorResponse);
        }
    }
}