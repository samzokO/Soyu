package com.ssafy.soyu.member.controller;

import com.ssafy.soyu.member.service.MemberService;
import com.ssafy.soyu.util.jwt.dto.response.TokenResponse;
import com.ssafy.soyu.util.naver.dto.NaverProfile;
import com.ssafy.soyu.util.naver.dto.request.NaverLoginRequest;
import com.ssafy.soyu.util.naver.service.NaverAuthService;
import com.ssafy.soyu.util.response.CommonResponseEntity;
import com.ssafy.soyu.util.response.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class NaverController {
    private final MemberService authService;
    private final NaverAuthService naverAuthService;

    //네이버 로그인
    @PostMapping("/login")
    public ResponseEntity<CommonResponseEntity> loginNaver(@RequestBody NaverLoginRequest request) {
        String code = request.getAuthorizationCode();
        String naverAccessToken = naverAuthService.getAccessToken(code).getAccessToken(); //네이버로부터 accessToken 발급 받기

        //사용자 정보 가져오거나 회원가입
        NaverProfile profile = naverAuthService.getUserInfo(naverAccessToken); //발급 받은 토큰으로 사용자 프로필 조회
        TokenResponse token = authService.login(profile); //사용자 프로필 기반 로그인 or 회원가입
        return CommonResponseEntity.getResponseEntity(SuccessCode.OK, token);
    }
}
