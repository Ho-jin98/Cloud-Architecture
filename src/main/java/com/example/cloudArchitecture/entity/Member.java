package com.example.cloudArchitecture.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "members")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer age;

    @Column(nullable = false)
    private String mbti;

    // 프로필 이미지는 필수가 아니라고 생각 -> 회원가입할 때 이미지 없어도 가입 가능 -> nullable = false 선언 X
    private String profileImageUrl;

    public Member(String name ,Integer age, String mbti) {
        this.name = name;
        this.age = age;
        this.mbti = mbti;
    }

    // URL 업데이트 하는 메서드
    public void update(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

}
