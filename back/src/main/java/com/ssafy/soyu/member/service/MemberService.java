package com.ssafy.soyu.member.service;

import com.ssafy.soyu.member.domain.Member;
import com.ssafy.soyu.util.jwt.dto.response.TokenResponse;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

public interface MemberService {
    public TokenResponse login(Member member, String naverEmail);
    public Member signUp(Member member);
    public Optional<Member> findByEmail(String email);

    TokenResponse recreateToken(String refreshToken, HttpServletRequest request);
}
