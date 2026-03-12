package com.example.cloudArchitecture.common;

import com.example.cloudArchitecture.exception.ErrorCode;
import com.example.cloudArchitecture.exception.ServerException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.io.IOException;
import java.time.Duration;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class S3Uploader {

    private final S3Client s3Client;

    private final S3Presigner s3Presigner;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.region.static}")
    private String region;

    // 업로드 메서드
    public String upload(MultipartFile image) {
        if (image == null || image.isEmpty()) return null;
        // 이미지가 없거나 비어있으면 null 반환
        try {
            // S3 에 저장할 이름 (UUID + 확장자)
            String originalImageName = image.getOriginalFilename();
            // ex) 홍길돌.jpg
            String extension = "";
            if (originalImageName != null && originalImageName.contains("."))
            {
                extension =
                        originalImageName.substring(originalImageName.lastIndexOf("."));
            }
            // → ".jpg"
            // lastIndexOf(".") → 마지막 "." 위치를 찾아서
            // substring()으로 "." 포함 뒤에 있는 확장자만 추출

            String imageNameForS3 = "images/" + UUID.randomUUID() +
                    extension;
            // → "images/랜덤 UUID값.jpg"
            // images/ 폴더 안에 UUID + 확장자로 저장

            // S3 에 업로드
            // PutObjectRequest -> "이런 조건으로 S3에 저장해줘" 라는 요청 명세서
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucket) // 어느 버킷에 저장할지
                    .key(imageNameForS3) // S3에 저장될 파일 경로/이름
                    .contentType(image.getContentType()) // "image/jpeg" 등 파일 타입
                    .contentLength(image.getSize()) // 파일 크기 (byte)
                    .build();

            // 실제 S3에 업로드
            s3Client.putObject(request, RequestBody.fromInputStream(
                    image.getInputStream(), image.getSize()));
            //request → 아까 만든 요청 명세서
            //RequestBody.fromInputStream() → 실제 이미지 데이터를 스트림으로 변환
            //image.getInputStream() → 이미지 파일의 실제 데이터
            //image.getSize() → 파일 크기

            // URL 반환
            return imageNameForS3;
            // S3에 저장된 이미지의 주소를 조합해서 반환
        } catch (IOException e) {
            throw new ServerException(ErrorCode.IMAGE_FAIL);
            // 전체 흐름
            // 이미지 받음 → UUID로 파일명 생성 → S3 업로드 요청 → 실제 업로드 → URL 반환
        }
    }

    public String getPresignedUrl(String imageName) {
        // 어떤 객체에 대한 URL을 만들지 요청 객체 생성
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucket) // 어느 버킷에서 가져올지
                .key(imageName) // S3에 지정된 파일 경로
                .build();

        // Presign 요청 만들기
        GetObjectPresignRequest getObjectPresignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofDays(7)) // 7일 후 만료
                .getObjectRequest(getObjectRequest) // 위에서 만든 요청 객체 연결
                .build();
        // Duration.ofDays() -> 자바가 제공하는 시간표현 클래스
        // Duration.ofDays(7) -> 7일
        // Duration.ofHours(24) -> 24시간
        // Duration.ofMinutes(30) -> 30분

        // Presign URL 생성
        PresignedGetObjectRequest presignedGetObjectRequest = s3Presigner.presignGetObject(getObjectPresignRequest);

        // URL 반환
        return presignedGetObjectRequest.url().toString();
    }
}


