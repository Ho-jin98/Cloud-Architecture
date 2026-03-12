package com.example.cloudArchitecture.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter

public class CreateMemberRequest {

    @Length(min = 2, max = 20, message = "이름은 필수입니다.")
    private String name;

    @Min(value = 0, message = "나이는 0 이상이어야 합니다.")
    @Max(value = 150, message = "나이는 150 이하이어야 합니다.")
    private Integer age;

    @Pattern(regexp = "^[EI][NS][TF][JP]$",
            message = "올바른 MBTI 형식이 아닙니다")
    private String mbti;
}
