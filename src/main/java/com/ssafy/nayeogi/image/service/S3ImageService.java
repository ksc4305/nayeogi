package com.ssafy.nayeogi.image.service;

import com.ssafy.nayeogi.image.model.dto.ImageUploadResponse;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Amazon S3에 이미지를 업로드하는 서비스 구현체.
 * 's3' 프로필이 활성화되었을 때 사용됩니다.
 *
 * pom.xml에 'spring-cloud-starter-aws' 의존성 추가 및
 * application.properties에 AWS 관련 설정이 필요합니다.
 */
@Service
@Profile("s3")
public class S3ImageService implements ImageService {

    // @Value("${cloud.aws.s3.bucket}")
    // private String bucket;

    // private final AmazonS3 amazonS3;

    // public S3ImageService(AmazonS3 amazonS3) {
    //     this.amazonS3 = amazonS3;
    // }

    @Override
    public ImageUploadResponse upload(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IOException("업로드할 파일이 비어있습니다.");
        }

        // 실제 S3 업로드 로직 (여기는 단순 골격입니다)
        // String originalFilename = file.getOriginalFilename();
        // String objectKey = UUID.randomUUID().toString() + "_" + originalFilename;
        //
        // ObjectMetadata metadata = new ObjectMetadata();
        // metadata.setContentLength(file.getSize());
        // metadata.setContentType(file.getContentType());
        //
        // amazonS3.putObject(bucket, objectKey, file.getInputStream(), metadata);
        //
        // String fileUrl = amazonS3.getUrl(bucket, objectKey).toString();
        // return new ImageUploadResponse(fileUrl);

        System.out.println("S3에 이미지 업로드 (구현 필요)");
        return new ImageUploadResponse("s3-upload-url-placeholder");
    }
}
