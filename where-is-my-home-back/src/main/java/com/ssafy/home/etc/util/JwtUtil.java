package com.ssafy.home.etc.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ssafy.home.etc.exception.BaseException;
import com.ssafy.home.user.dto.UserDto;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    @Value("${jwt.secretKey}")
    private String secret;
    private Algorithm key;

    @PostConstruct
    public void setKey() {
        key = Algorithm.HMAC256(secret);
    }

    // 현재 accessToken : 10분/ refreshToken : 1시간 => 배포시 늘려야됨
    public String getAccessToken(UserDto userDto) {
        return JWT.create()
            .withSubject("accessToken")
            .withAudience(userDto.getId().toString())
            .withClaim("nickname", userDto.getNickname())
            .withClaim("role", userDto.getRole())
            .withExpiresAt(Date.from(LocalDateTime.now().plusMinutes(10).atZone(ZoneId.systemDefault()).toInstant()))
            .sign(key);
    }

    public String getRefreshToken(UserDto userDto) {
        return JWT.create()
            .withSubject("refreshToken")
            .withAudience(userDto.getId().toString())
            .withExpiresAt(Date.from(LocalDateTime.now().plusHours(1).atZone(ZoneId.systemDefault()).toInstant()))
            .sign(key);
    }

    public void isValidForm(String token) {
        // 토큰이 들어왔는가?
        if (token == null) {
            throw new BaseException("token is null", HttpStatus.BAD_REQUEST);
        }

        // 토큰이 "Bearer "로 시작하는가?
        if (!token.startsWith("Bearer ")) {
            throw new BaseException("token is not start with \"Bearer \"", HttpStatus.BAD_REQUEST);
        }

        // 토큰이 "Bearer " 이후로 존재하는가?
        if (token.length() < 8) {
            throw new BaseException("token is too short", HttpStatus.BAD_REQUEST);
        }
    }

    // 1. 토큰 타입이 올바른가?
    // 2. 토큰 서명이 일치하는가?
    // 3. 토큰 발행 대상자가 존재하는가?
    public void isValidToken(String token, String tokenType) {
        try {
            DecodedJWT decodedJWT = JWT.require(key)
                .withSubject(tokenType)
                .build()
                .verify(token);

            if (decodedJWT.getAudience().isEmpty()) {
                throw new JWTVerificationException("NotValidToken");
            }
        } catch (TokenExpiredException e) {
            // 토큰 만료시
            throw new BaseException("TokenExpiredException", HttpStatus.UNAUTHORIZED);
        } catch (JWTVerificationException e) {
            // 다른 경우는 모두 인증 실패
            throw new BaseException("JWTVerificationException", HttpStatus.BAD_REQUEST);
        }
    }

    // 검증 없이 토큰 디코딩
    public DecodedJWT getDecodedJWT(String token) {
        try {
            return JWT.decode(token);
        } catch (JWTDecodeException e) {
            throw new BaseException("Decode fail", HttpStatus.BAD_REQUEST);
        }
    }
}
