package com.ssafy.nayeogi.image.service;

import com.ssafy.nayeogi.image.model.dto.ImageUploadResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 로컬 파일 시스템에 이미지를 저장하는 서비스 구현체.
 * 'local' 프로필이 활성화되었을 때 사용됩니다.
 */
@Service
@Profile("local")
public class LocalImageService implements ImageService {

    // application.properties 등에서 `file.upload-dir=/path/to/upload` 와 같이 설정
    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public ImageUploadResponse upload(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IOException("업로드할 파일이 비어있습니다.");
        }

        File uploadPath = new File(uploadDir);
        if (!uploadPath.exists()) {
            uploadPath.mkdirs();
        }

        // 파일명 중복을 피하기 위해 UUID 사용
        String originalFilename = file.getOriginalFilename();
        String storedFilename = UUID.randomUUID().toString() + "_" + originalFilename;
        File dest = new File(uploadPath, storedFilename);
        file.transferTo(dest);

        ImageUploadResponse response = new ImageUploadResponse();
        response.setId(storedFilename.hashCode());
        return response;
    }
}
