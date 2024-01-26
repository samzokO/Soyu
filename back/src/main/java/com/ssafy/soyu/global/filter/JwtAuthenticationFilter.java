package com.ssafy.soyu.global.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.soyu.util.jwt.JwtTokenProvider;
import com.ssafy.soyu.util.response.ErrorCode;
import com.ssafy.soyu.util.response.ErrorResponseEntity;
import com.ssafy.soyu.util.response.exception.CustomException;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class JwtAuthenticationFilter implements Filter {

    private final JwtTokenProvider jwtTokenProvider;
    private final static String main = "/";
    private final static List<String> whiteList = new ArrayList<>();
    static {
        whiteList.add("/naver");
        whiteList.add("/member/token");
        whiteList.add("/swagger-ui");
        whiteList.add("/v3/api-docs");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        String requestURI = httpServletRequest.getRequestURI();

        //main url 이거나 whiteList url이면 다음 필터로
        if (requestURI.equals(main) || checkWhiteList(requestURI)) {
            chain.doFilter(request, response);
            return;
        }

        try{
            String accessToken = resolveToken((HttpServletRequest) request);
            Long memberId = null;

            //비회원인 경우
            if(accessToken == null){
                request.setAttribute("memberId", memberId);
                chain.doFilter(request,response);
            }
            //회원인 경우
            if (accessToken != null && jwtTokenProvider.validateToken(accessToken)) {
                // 토큰이 유효할 경우
                memberId = jwtTokenProvider.getMemberIdFromToken(accessToken);
                request.setAttribute("memberId", memberId);
                chain.doFilter(request, response);
            }
        }catch (StringIndexOutOfBoundsException e) {
            throw new CustomException(ErrorCode.NOT_FOUND_AUTH_TOKEN);
        } catch (CustomException e) {
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            setErrorResponse(httpServletResponse, e.getErrorCode());
        }
    }

    private void setErrorResponse(HttpServletResponse response, ErrorCode ec) {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(ec.getHttpStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE + "; charset=utf-8");
        ErrorResponseEntity errorResponseEntity = ErrorResponseEntity.builder()
                .statusCode(ec.getHttpStatus().value())
                .statusName(ec.name())
                .message(ec.getMessage())
                .build();
        try {
            response.getWriter().write(objectMapper.writeValueAsString(errorResponseEntity));
        } catch (IOException e) {
            e.printStackTrace();
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
