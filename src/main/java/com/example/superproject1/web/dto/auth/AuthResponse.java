package com.example.superproject1.web.dto.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    @Schema(description = "상태 코드")
    private int code;
    @Schema(description = "상태 메시지")
    private String message;
    @Schema(description = "반환된 회원관련 정보")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;
}
