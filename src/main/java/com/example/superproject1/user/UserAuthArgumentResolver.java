package com.example.superproject1.user;

import com.example.superproject1.user.dto.AuthInfo;
import lombok.AllArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Map;

@Component
@AllArgsConstructor
public class UserAuthArgumentResolver implements HandlerMethodArgumentResolver {
    private final JwtService jwtService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(AuthInfo.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory){

        String token = webRequest.getHeader("Authorization");

        if(token == null){
            throw new RuntimeException("UnauthorizedException");
        }

        Map<String, Long> decodedToken = jwtService.decode(token);
        Long userId = decodedToken.get(JwtService.CLAIM_NAME_USER_ID);

        if(userId == null){
            throw new RuntimeException("UnauthorizedException");
        }

        return AuthInfo.of(userId);
    }
}
