package com.ssafy.soyu.member.controller;

import com.ssafy.soyu.member.dto.request.AccountDto;
import com.ssafy.soyu.member.service.MemberService;
import com.ssafy.soyu.util.jwt.dto.response.TokenResponse;
import com.ssafy.soyu.util.response.CommonResponseEntity;
import com.ssafy.soyu.util.response.ErrorCode;
import com.ssafy.soyu.util.response.SuccessCode;
import com.ssafy.soyu.util.response.exception.CustomException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    //토큰 재발급 == filter 안거침 == 헤더에서 직접 가져와야함.
    @PostMapping("/token")
    public ResponseEntity<?> recreateToken
            (@RequestHeader(value = "Authorization", required = false) String bearerToken) {
        String refreshToken = memberService.getToken(bearerToken);

        //리프레시토큰 -> 토큰 검증 -> 토큰 재발급 -> db저장
        TokenResponse token = memberService.recreateToken(refreshToken);
        return CommonResponseEntity.getResponseEntity(SuccessCode.OK, token);
    }

    @PatchMapping("/account")
    public ResponseEntity<?> registrationAccount(@RequestBody AccountDto accountDto, HttpServletRequest request){
        // HttpServletRequest에서  멤버 id 가져오기
        Long memberId = (Long) request.getAttribute("memberId");
        System.out.println(memberId);
        if(memberId == null) throw new CustomException(ErrorCode.USER_NOT_FOUND);
        memberService.updateAccount(memberId, accountDto);

        return CommonResponseEntity.getResponseEntity(SuccessCode.OK);
    }
}
