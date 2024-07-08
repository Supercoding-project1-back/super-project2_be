package com.example.superproject1.web.controller.auth;

import com.example.superproject1.repository.users.userDetails.CustomUserDetails;
import com.example.superproject1.service.authService.AuthService;
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

    @GetMapping("/test")
    public Object test(@AuthenticationPrincipal CustomUserDetails customUserDetails){
        return customUserDetails.toString();
    }
}
