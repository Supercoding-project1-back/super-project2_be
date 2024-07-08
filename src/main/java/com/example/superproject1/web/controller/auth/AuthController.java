package com.example.superproject1.web.controller.auth;

import com.example.superproject1.repository.users.userDetails.CustomUserDetails;
import com.example.superproject1.service.authService.AuthService;
import com.example.superproject1.service.exceptions.AccessDeniedException;
import com.example.superproject1.service.exceptions.NotAcceptableException;
import com.example.superproject1.web.dto.auth.AuthResponseDto;
import com.example.superproject1.web.dto.auth.LoginRequest;
import com.example.superproject1.web.dto.auth.SignupRequest;
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

    @PostMapping("/sign-up")
    public AuthResponseDto signUp(@RequestBody SignupRequest signupRequest){
        return authService.signUp(signupRequest);
    }

    @PostMapping("/login")
    public AuthResponseDto login(@RequestBody LoginRequest loginRequest, HttpServletResponse httpServletResponse){
        List<Object> tokenAndResponse = authService.login(loginRequest);
        httpServletResponse.setHeader("Token", (String) tokenAndResponse.get(0));
        return (AuthResponseDto) tokenAndResponse.get(1);
    }

    @GetMapping(value = "/entrypoint")
    public void entrypointException(@RequestParam(name = "token", required = false) String token) {
        if (token==null) throw new NotAcceptableException("로그인(Jwt 토큰)이 필요합니다.", null);
        else throw new NotAcceptableException("로그인이 만료 되었습니다.","유효하지 않은 토큰 : "+ token);
    }

    @GetMapping(value = "/access-denied")
    public void accessDeniedException(@RequestParam(name = "roles", required = false) String roles) {
        if(roles==null) throw new AccessDeniedException("권한이 설정되지 않았습니다.",null);
        else throw new AccessDeniedException("권한이 없습니다.", "시도한 유저의 권한 : "+roles);
    }

    @GetMapping("/test1")
    public Object test1(@AuthenticationPrincipal CustomUserDetails customUserDetails){
        return customUserDetails.toString();
    }

    @GetMapping("/test2")
    public String test2(){
        return "Jwt 토큰이 상관없는 EndPoint 테스트입니다.";
    }
}
