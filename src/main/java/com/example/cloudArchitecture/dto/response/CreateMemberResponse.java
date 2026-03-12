package com.example.cloudArchitecture.dto.response;

import lombok.Getter;

@Getter

public class CreateMemberResponse {
    private final Long id;
    private final String name;
    private final Integer age;
    private final String mbti;

    public CreateMemberResponse(Long id, String name, Integer age, String mbti) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.mbti = mbti;
    }
}
