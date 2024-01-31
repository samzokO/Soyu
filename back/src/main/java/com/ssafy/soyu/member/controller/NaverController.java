package com.ssafy.soyu.member.controller;

import com.ssafy.soyu.member.entity.Member;
import com.ssafy.soyu.member.service.MemberService;
import com.ssafy.soyu.util.jwt.dto.response.TokenResponse;
import com.ssafy.soyu.util.naver.NaverProperties;
import com.ssafy.soyu.util.naver.dto.NaverProfile;
import com.ssafy.soyu.util.naver.dto.request.NaverLoginRequest;
import com.ssafy.soyu.util.naver.service.NaverAuthService;
import com.ssafy.soyu.util.response.CommonResponseEntity;
import com.ssafy.soyu.util.response.SuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/naver")
@Tag(name = "Naver 컨트롤러", description = "Naver API 입니다.")
public class NaverController {
    private final MemberService memberService;
    private final NaverAuthService naverAuthService;
    private final NaverProperties naverProperties;

    @GetMapping("")
    public String naver() throws Exception {
        return "https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=" + naverProperties.getClientId()
                + "&state=" + naverProperties.getState()
                + "&redirect_uri=" + naverProperties.getRedirectUri();
    }

    @PostMapping("/login")
    @Operation(summary = "네이버 로그인", description = "NaverLoginRequest를 이용해 네이버로 간편하게 로그인합니다.")
    public ResponseEntity<CommonResponseEntity> loginNaver(@RequestBody NaverLoginRequest request) {
        String code = request.getAuthorizationCode();
        String naverAccessToken = naverAuthService.getAccessToken(code).getAccessToken(); //네이버로부터 accessToken 발급 받기

        //사용자 정보 가져오거나 회원가입
        NaverProfile profile = naverAuthService.getUserInfo(naverAccessToken); //발급 받은 토큰으로 사용자 프로필 조회

        Member member = memberService.findByEmail(profile.getEmail()).orElseGet(()-> naverSignUp(profile));
        TokenResponse token = memberService.login(member); //사용자 프로필 기반 로그인 or 회원가입
        return CommonResponseEntity.getResponseEntity(SuccessCode.OK, token);
    }

    private Member naverSignUp(NaverProfile profile){
        return memberService.signUp(naverAuthService.makeNewUser(profile));
    }
}
