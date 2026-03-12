package com.example.cloudArchitecture.controller;

import com.example.cloudArchitecture.common.CommonResponse;
import com.example.cloudArchitecture.dto.request.CreateMemberRequest;
import com.example.cloudArchitecture.dto.response.CreateMemberResponse;
import com.example.cloudArchitecture.dto.response.GetMemberResponse;
import com.example.cloudArchitecture.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")

public class MemberController {

    private final MemberService  memberService;

    @PostMapping
    public ResponseEntity<CommonResponse<CreateMemberResponse>> createMember(@Valid @RequestBody CreateMemberRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(CommonResponse.success(memberService.save(request)));
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<CommonResponse<GetMemberResponse>> getMember(@PathVariable Long memberId){
        return ResponseEntity.status(HttpStatus.OK).body(CommonResponse.success(memberService.getMember(memberId)));
    }

    @PostMapping("/{memberId}/profile-image")
    public ResponseEntity<CommonResponse<Void>> updateProfileImage(
            @PathVariable Long memberId,
            @RequestParam("image") MultipartFile image) {
        memberService.updateProfileImage(memberId, image);
        return ResponseEntity.ok(CommonResponse.success(null));
    }

    @GetMapping("/{memberId}/profile-image")
    public ResponseEntity<CommonResponse<String>> getPresignedUrl(
            @PathVariable Long memberId) {
        return ResponseEntity.status(HttpStatus.OK).body(CommonResponse.success(memberService.getPresignedUrl(memberId)));
    }
}
