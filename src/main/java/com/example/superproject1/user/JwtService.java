package com.example.superproject1.user;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@Service
public class JwtService {
    @Value("supercoding")
    private String secretKey;
    public static final String CLAIM_NAME_USER_ID = "userId";
    private Algorithm algorithm;
    private JWTVerifier jwtVerifier;

    @PostConstruct
    private void init(){
        algorithm = Algorithm.HMAC256(secretKey);
        jwtVerifier = JWT.require(algorithm).build();
    }

    public String encode(Long userId){
        LocalDateTime expiredAt = LocalDateTime.now().plusHours(5L);
        Timestamp date = Timestamp.valueOf(expiredAt);

        return JWT.create()
                .withClaim(CLAIM_NAME_USER_ID, userId)
                .withExpiresAt(date)
                .sign(algorithm);
    }

    public Map<String, Long> decode(String token){
        try{
            DecodedJWT jwt = jwtVerifier.verify(token);
            return Map.of(CLAIM_NAME_USER_ID, jwt.getClaim(CLAIM_NAME_USER_ID).asLong());
        }catch (JWTVerificationException e){
            log.warn("Failed to decode jwt. token: {}", token, e);
            return null;
        }
    }
}
