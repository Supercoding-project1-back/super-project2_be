package com.example.superproject1.web.controller.auth;

import com.example.superproject1.repository.users.userDetails.CustomUserDetails;
import com.example.superproject1.service.authService.AuthService;
import com.example.superproject1.service.exceptions.AccessDeniedException;
import com.example.superproject1.service.exceptions.NotAcceptableException;
import com.example.superproject1.web.dto.auth.AuthResponse;
import com.example.superproject1.web.dto.auth.LoginRequest;
import com.example.superproject1.web.dto.auth.SignupRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "회원 가입", description = "회원 가입을 합니다.")
    @PostMapping("/sign-up")
    public AuthResponse signUp(@Parameter(description = "새로 생성할 회원 정보") @RequestBody SignupRequest signupRequest){
        return authService.signUp(signupRequest);
    }

    @Operation(summary = "로그인", description = "로그인을 합니다.")
    @PostMapping("/login")
    public AuthResponse login(@Parameter(description = "로그인 정보") @RequestBody LoginRequest loginRequest, HttpServletResponse httpServletResponse){
        List<Object> tokenAndResponse = authService.login(loginRequest);
        httpServletResponse.setHeader("Token", (String) tokenAndResponse.get(0));
        return (AuthResponse) tokenAndResponse.get(1);
    }

    @Operation(summary = "토큰관련 에러", description = "토큰이 없거나 만료된 경우 메시지가 나옵니다.")
    @GetMapping(value = "/entrypoint")
    public void entrypointException(@Parameter(description = "요청된 token 값") @RequestParam(name = "token", required = false) String token) {
        if (token==null) throw new NotAcceptableException("로그인(Jwt 토큰)이 필요합니다.", null);
        else throw new NotAcceptableException("로그인이 만료 되었습니다.","유효하지 않은 토큰 : "+ token);
    }

    @Operation(summary = "유저 권한 에러", description = "유저 권한이 ROLE_USER 가 아닐때 메시지가 나옵니다.")
    @GetMapping(value = "/access-denied")
    public void accessDeniedException(@Parameter(description = "확인할 Role 값") @RequestParam(name = "roles", required = false) String roles) {
        if(roles==null) throw new AccessDeniedException("권한이 설정되지 않았습니다.",null);
        else throw new AccessDeniedException("권한이 없습니다.", "시도한 유저의 권한 : "+roles);
    }

    @Operation(summary = "Jwt 토큰 인증 기능 테스트", description = "토큰을 첨부하여 실행하면 회원 정보가 조회됩니다.")
    @GetMapping("/test1")
    public Object test1(@Parameter(description = "토큰으로 조회하는 회원 정보") @AuthenticationPrincipal CustomUserDetails customUserDetails){
        return customUserDetails.toString();
    }

    @Operation(summary = "Jwt 토큰 인증 상관없는 엔트리포인트 테스트", description = "토큰이 상관 없는 엔트리포인트 테스트입니다.")
    @GetMapping("/test2")
    public String test2(){
        return "Jwt 토큰이 상관없는 EntryPoint 테스트입니다.";
    }
}
