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
public class S3ImageService implements ImageService {

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
            String s3Key = UUID.randomUUID() + "." + extension; // 중복 방지 파일명

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
            // 삭제 실패해도 에러를 던지지 않고 로그만 남기는 게 좋을 수 있음 (사용자 경험상)
            // log.error("S3 파일 삭제 실패: {}", e.getMessage());
            throw new RuntimeException("파일 삭제 실패", e);
        }
    }

    // URL에서 파일명만 쏙 빼내는 헬퍼 메서드
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
}