package com.ssafy.soyu.util.jwt;

import com.ssafy.soyu.util.response.ErrorCode;
import com.ssafy.soyu.util.response.exception.CustomException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final static String main = "/";
    private final static List<String> whiteList = new ArrayList<>();
    static {
        whiteList.add("/naver");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        if (requestURI.equals(main) || checkWhiteList(requestURI)) {
            filterChain.doFilter(request, response);
            return;
        }

        try{
            String accesstoken = Optional.ofNullable(resolveToken((HttpServletRequest) request))
                    .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_AUTH_TOKEN));

            if (accesstoken != null && jwtTokenProvider.validateToken(accesstoken)) {
                // 토큰이 유효할 경우 토큰에서 Authentication 객체를 가지고 와서 SecurityContext에 저장

                Authentication authentication = jwtTokenProvider.getAuthentication(accesstoken, request);

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (StringIndexOutOfBoundsException e) {
            throw new CustomException(ErrorCode.NOT_FOUND_AUTH_TOKEN);
        }
    }

    // 헤더에서 토큰 추출
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private boolean checkWhiteList(String requestURI) {
        for (String white : whiteList) {
            if(requestURI.startsWith(white)) {
                return true;
            }
        }
        return false;
    }
}
