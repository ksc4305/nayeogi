package com.ssafy.nayeogi.image.service;

import io.awspring.cloud.s3.S3Template;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
                
                // 업로드된 URL 생성 (S3 버킷 설정에 따라 URL 구조가 다를 수 있음)
                String url = "https://" + bucketName + ".s3.ap-northeast-2.amazonaws.com/" + s3Key;
                imageUrls.add(url);
                
            } catch (IOException e) {
                throw new RuntimeException("이미지 업로드 실패: " + originalName, e);
            }
        }
        return imageUrls;
    }
}