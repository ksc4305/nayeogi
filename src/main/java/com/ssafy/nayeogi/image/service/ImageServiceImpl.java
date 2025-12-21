package com.ssafy.nayeogi.image.service;

import io.awspring.cloud.s3.S3Template;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final S3Template s3Template;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucketName;

    @Override
    public List<String> upload(List<MultipartFile> files) {
        List<String> imageUrls = new ArrayList<>();

        for (MultipartFile file : files) {
            if (file.isEmpty()) continue;

            String originalName = file.getOriginalFilename();
            String extension = FilenameUtils.getExtension(originalName);
            String s3Key = "temp-uploads/" + UUID.randomUUID() + "." + extension; // 중복 방지 파일명

            try {
                // S3에 업로드
                s3Template.upload(bucketName, s3Key, file.getInputStream());
                
                // 업로드된 URL 생성
                String url = "https://" + bucketName + ".s3.ap-northeast-2.amazonaws.com/" + s3Key;
                imageUrls.add(url);
                
            } catch (IOException e) {
                throw new RuntimeException("이미지 업로드 실패: " + originalName, e);
            }
        }
        return imageUrls;
    }
    
    @Override
    public String moveImageToPermanent(String tempImageUrl) {
	   String tempKey = extractKeyFromUrl(tempImageUrl);
	    
	    // "temp-uploads/" prefix가 없거나, 이미 영구 경로에 있는 경우, 원본 URL 그대로 반환
	    if (!tempKey.startsWith("temp-uploads/")) {
	        return tempImageUrl;
	    }
	    
	    // 1. 새 영구 경로 키 생성
	    String permanentKey = tempKey.replace("temp-uploads/", "permanent-media/");
	    
	    try {
	        // 2. 임시 경로 -> 영구 경로로 객체 복사
//	        s3Template.copyObject(bucketName, tempKey, bucketName, permanentKey);
	    
	        // 3. 임시 경로의 원본 객체 삭제
	        s3Template.deleteObject(bucketName, tempKey);
	    
	        // 4. 새로운 영구 URL 생성 및 반환
	        return "https://" + bucketName + ".s3.ap-northeast-2.amazonaws.com/" + permanentKey;
	    
	    } catch (Exception e) {
	        throw new RuntimeException("파일 영구 저장 실패: " + tempImageUrl, e);
	    }
    }
    

    @Override
    public void delete(String imageUrl) {
        // 1. URL에서 S3 Key(파일명) 추출하기
        // 예: https://버킷.s3...com/uuid_image.jpg -> "uuid_image.jpg"
        String key = extractKeyFromUrl(imageUrl);

        try {
            // 2. S3에서 삭제 요청
            // (한글 파일명 등이 있을 수 있으니 디코딩 처리)
            String decodedKey = URLDecoder.decode(key, StandardCharsets.UTF_8);
            s3Template.deleteObject(bucketName, decodedKey);
        } catch (Exception e) {
            // log.error("S3 파일 삭제 실패: {}", e.getMessage());
            throw new RuntimeException("파일 삭제 실패", e);
        }
    }

    // URL에서 파일명만 쏙
    private String extractKeyFromUrl(String imageUrl) {
        try {
            // "amazonaws.com/" 뒷부분을 찾아서 자름
            String splitStr = ".amazonaws.com/";
            int index = imageUrl.indexOf(splitStr);
            if (index != -1) {
                return imageUrl.substring(index + splitStr.length());
            }
            // 만약 URL 형식이 다르면, 마지막 슬래시(/) 뒤를 가져옴
            return imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
        } catch (Exception e) {
            throw new IllegalArgumentException("잘못된 이미지 URL입니다.");
        }
    }
    
    @Override
    public String transformImage(String originalUrl, String style) {
        // TODO: 실제 AI 이미지 변환 API 연동 필요 (예: DALL-E, Stability AI 등)
        
        // 현재는 가짜 URL 반환 (MVP)
        // 실제로는 여기서 외부 API를 호출하고, 받은 이미지를 다시 S3에 저장한 뒤 그 URL을 리턴해야 함.
        return originalUrl + "_transformed_" + style + ".jpg"; 
    }
}