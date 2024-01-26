package com.ssafy.soyu.global.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.soyu.util.response.ErrorCode;
import com.ssafy.soyu.util.response.ErrorResponseEntity;
import com.ssafy.soyu.util.response.exception.CustomException;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NonMemberExceptionFilter implements Filter {

    private final static String main = "/";
    private final static List<String> whiteList = new ArrayList<>();
    static {
        //비회원도 접근 가능한 uri는 추가
        whiteList.add("/naver");
        whiteList.add("/member/token");
        whiteList.add("/swagger-ui");
        whiteList.add("/v3/api-docs");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        String requestURI = httpServletRequest.getRequestURI();

        //main url 이거나 whiteList url이면 다음 필터로
        if (requestURI.equals(main) || checkWhiteList(requestURI)) {
            chain.doFilter(request, response);
            return;
        }

        try {
            // memberId 속성을 가져옴
            Long memberId = (Long) httpServletRequest.getAttribute("memberId");

            // memberId가 null인 경우, 즉 비회원인 경우 CustomException을 던짐
            if (memberId == null) {
                throw new CustomException(ErrorCode.NON_MEMBER_ACCESS);
            }

            // 다음 필터로 요청을 넘김
            chain.doFilter(request, response);
        } catch (CustomException e) {
            // Custom exception을 처리하는 방식, 예를 들어 오류 응답을 설정
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


    private boolean checkWhiteList(String requestURI) {
        for (String white : whiteList) {
            if(requestURI.startsWith(white)) {
                return true;
            }
        }
        return false;
    }
}
