package com.example.superproject1.web.dto.auth;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignupResponse {
    private Long userId;
    private String name;
}
