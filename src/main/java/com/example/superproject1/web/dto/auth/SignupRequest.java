package com.example.superproject1.web.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class SignupRequest {
    @Schema(description = "회원 이메일")
    private String email;
    @Schema(description = "회원 비밀번호")
    private String password;
    @Schema(description = "회원 비밀번호 확인")
    private String passwordConfirm;
    @Schema(description = "회원 이름")
    private String name;
    @Schema(description = "회원 전화번호")
    private String phoneNumber;
    @Schema(description = "회원 주소")
    private String address;
    @Schema(description = "회원 성별")
    private String gender;
    @Schema(description = "회원 생년월일")
    private String birthDate;
}
