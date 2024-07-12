package com.example.superproject1.web.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignupResponse {
    @Schema(description = "회원 id")
    private Long userId;
    @Schema(description = "회원 이름")
    private String name;
}
