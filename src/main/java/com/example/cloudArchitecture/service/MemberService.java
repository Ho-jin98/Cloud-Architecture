package com.example.cloudArchitecture.service;

import com.example.cloudArchitecture.common.S3Uploader;
import com.example.cloudArchitecture.dto.request.CreateMemberRequest;
import com.example.cloudArchitecture.dto.response.CreateMemberResponse;
import com.example.cloudArchitecture.dto.response.GetMemberResponse;
import com.example.cloudArchitecture.entity.Member;
import com.example.cloudArchitecture.exception.ErrorCode;
import com.example.cloudArchitecture.exception.MemberException;
import com.example.cloudArchitecture.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final S3Uploader s3Uploader;

    @Transactional
    public CreateMemberResponse save(CreateMemberRequest request) {
        Member member = new Member(
                request.getName(),
                request.getAge(),
                request.getMbti()
        );
        Member savedMember = memberRepository.save(member);
        return new CreateMemberResponse(
                savedMember.getId(),
                savedMember.getName(),
                savedMember.getAge(),
                savedMember.getMbti()
        );
    }

    @Transactional(readOnly = true)
    public GetMemberResponse getMember(Long memberId){
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new MemberException(ErrorCode.MEMBER_NOT_FOUND)
        );
        return new GetMemberResponse(
                member.getId(),
                member.getName(),
                member.getAge(),
                member.getMbti()
        );
    }

    @Transactional
    public void updateProfileImage(Long memberId, MultipartFile image) {
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new MemberException(ErrorCode.MEMBER_NOT_FOUND)
        );
        String imageUrl = s3Uploader.upload(image);
        member.update(imageUrl);
    }

    @Transactional(readOnly = true)
    public String getPresignedUrl(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new MemberException(ErrorCode.MEMBER_NOT_FOUND)
        );
        return s3Uploader.getPresignedUrl(member.getProfileImageUrl());
    }
}
