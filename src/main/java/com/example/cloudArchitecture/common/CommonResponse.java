package com.example.cloudArchitecture.common;

import com.example.cloudArchitecture.exception.ErrorResponse;
import lombok.Getter;

@Getter
public class CommonResponse<T> {
    private final boolean success;
    private final T data;
    private final ErrorResponse error;

    public CommonResponse(boolean success, T data, ErrorResponse error) {
        this.success = success;
        this.data = data;
        this.error = error;
    }

    // 성공
    public static <T> CommonResponse<T> success(T data) {
        return new CommonResponse<>(true, data, null);
    }

    // 실패
    public static <T> CommonResponse<T> fail(ErrorResponse error) {
        return new CommonResponse<>(false, null, error);
    }
}

// CommonResponse와 ErrorResponse가 역할이 겹칠수도 있어서 명확하게 구분하자!
// CommonResponse -> 클라이언트에게 응답을 줄 때 "응답 포맷"만 신경쓰기 (성공/실패 여부 + 데이터or에러)
// ErrorResponse -> 실제 디테일한 에러 정보만을 담당!!

// 둘 중 하나를 없애고 한 가지만 사용하는게 깔끔하지 않을까?
// => 코드는 단순해질순 있어도, 클라이언트에게 응답이 갔을 때 null 범벅이 될 수도 있음
// 하지만 두개로 나누면 코드는 복잡해지지만, 각자의 역할을 명확하게 나눠주면 의미가 있고, 응답이 깔끔해짐

