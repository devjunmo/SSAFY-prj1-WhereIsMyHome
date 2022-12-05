package com.ssafy.home.etc.interceptor;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.ssafy.home.etc.annotation.Auth;
import com.ssafy.home.etc.exception.BaseException;
import com.ssafy.home.etc.util.JwtUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod method = (HandlerMethod) handler;

        // Auth 어노테이션이 없으면 true
        Auth auth = method.getMethodAnnotation(Auth.class);
        if(auth == null) {
            return true;
        }

        // JWT 토큰 입력값 인증
        String accessToken = request.getHeader("Authorization");
        jwtUtil.isValidForm(accessToken);

        // 토큰이 유효한 토큰인지 인증
        accessToken = accessToken.substring(7);
        jwtUtil.isValidToken(accessToken, "accessToken");

        // 권한 인증
        DecodedJWT payload = jwtUtil.getDecodedJWT(accessToken);
        if (auth.type().equals("ADMIN") && !payload.getClaim("role").asString().equals("ADMIN")) {
            throw new BaseException("no authorization", HttpStatus.UNAUTHORIZED);
        }

        return true;
    }
}
