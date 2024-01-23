package com.ssafy.soyu.member.controller;

import com.ssafy.soyu.member.service.MemberService;
import com.ssafy.soyu.util.jwt.dto.response.TokenResponse;
import com.ssafy.soyu.util.response.CommonResponseEntity;
import com.ssafy.soyu.util.response.SuccessCode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    //토큰 재발급
    @PostMapping("/token")
    public ResponseEntity<?> recreateToken
            (@RequestHeader(value = "Authorization", required = false) String bearerToken, HttpServletRequest request) {
        String refreshToken = null;
        if (bearerToken.startsWith("Bearer"))
            refreshToken = bearerToken.substring(7);
        //리프레시토큰 -> 토큰 검증 -> 토큰 재발급 -> db저장
        TokenResponse token = memberService.recreateToken(refreshToken, request);
        return CommonResponseEntity.getResponseEntity(SuccessCode.OK, token);
    }
}
