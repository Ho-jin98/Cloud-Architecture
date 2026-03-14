package com.example.cloudArchitecture.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@Configuration
public class S3Config {
    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .region(Region.AP_NORTHEAST_2)
                .build();
    }

    @Bean
    public S3Presigner s3Presigner() {
        return S3Presigner.builder()
                .region(Region.AP_NORTHEAST_2)
                .build();
    }
}

/* S3Client는 AWS SDK에서 제공하는 클래스인데,
 build.gradle에 추가한 implementation 'io.awspring.cloud:spring-cloud-aws-starter-s3'
 해당 의존성이 추가될 때 같이 들어옴, 참고로 내부적으로 AWS SDK (software.amazon.awssdk:s3)를 포함하고 있음,*/

/* 빌더 (builder)는 갑자기 어떻게 나온 걸까?
 -> AWS SDK 내부에서 이미 빌더 패턴으로 설계되어 있어서 저렇게 호출할 수 있음
 AWS 개발자들이 S3Client를 만들 때 빌더 패턴으로 설계를해놓은 것!
* S3Client -> AWS SDK에서 제공하는 클래스
* builder() -> AWS SDK 내부에서 이미 빌더 패턴으로 설계되어 있어서 사용 가능! */

/* 왜 region만 추가를 할까?
 S3Client가 AWS에 요청을 보낼 때 "어느 지역 서버로 보낼지"만 알면 됨,
 (만약, region을 안넣으면 어느 서버로 요청해야 할지 몰라서 에러 발생)

 나머지 인증 정보 (EC2, RDS 등등)는 왜 안넣을까?
 IAM Role 방식이라서 AWS가 자동으로 처리해줌!
 EC2 시작 -> AWS가 EC2에 임시 자격증명 자동 발급 -> S3Client가 그 자격증명을 자동으로 가져다 사용
 -> 개발자는 아무것도 안 해도 됨!
 이것을 AWS DefaultCredentialsProvider 라고 부른다
 S3Client는 내부적으로 자격증명을 다음과 같은 순서로 자동 탐지
 1. 환경 변수
 2. yml 설정 값
 3. EC2 IAM Role 그래서 코드에 따로 안 넣어도 EC2 IAM Role이 있으면 자동으로 인증됨!!!! */


