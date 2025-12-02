package com.ssafy.nayeogi.image.service;

import com.ssafy.nayeogi.image.model.dto.ImageUploadResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 이미지 처리 관련 비즈니스 로직을 위한 공통 서비스 인터페이스
 */
public interface ImageService {

    /**
     * 이미지 파일을 업로드합니다.
     *
     * @param file 업로드할 이미지 파일
     * @return 업로드 결과 정보 (e.g., 저장된 URL)
     * @throws IOException 파일 처리 중 오류 발생 시
     */
    ImageUploadResponse upload(MultipartFile file) throws IOException;
}
