package com.example.superproject1.web.dto.auth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class SignupRequest {
    private String email;
    private String password;
    private String passwordConfirm;
    private String name;
    private String phoneNumber;
    private String address;
    private String gender;
    private String birthDate;
}
