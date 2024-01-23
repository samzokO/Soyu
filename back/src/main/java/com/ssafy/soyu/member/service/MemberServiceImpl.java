package com.ssafy.soyu.member.service;

import com.ssafy.soyu.member.domain.Member;
import com.ssafy.soyu.member.repository.MemberRepository;
import com.ssafy.soyu.util.jwt.JwtTokenProvider;
import com.ssafy.soyu.util.jwt.domain.RefreshToken;
import com.ssafy.soyu.util.jwt.dto.response.TokenResponse;
import com.ssafy.soyu.util.jwt.repository.AuthRepository;
import com.ssafy.soyu.util.naver.dto.NaverProfile;
import com.ssafy.soyu.util.response.ErrorCode;
import com.ssafy.soyu.util.response.exception.CustomException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final AuthRepository authRepository;
    private final JwtTokenProvider jwtTokenProvider;

    private final static String RAW_PASSWORD = "samjoko_is_god";

    @Override
    public TokenResponse login(Member member, String naverEmail) {
        // 1. Login email/password를 기반으로 Authentication 객체 생성
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(naverEmail, RAW_PASSWORD);
        //accessToken, refreshToken 발급
        TokenResponse token = jwtTokenProvider.createToken(authenticationToken, member.getId().toString());

        //DB에 refreshToken 저장
        RefreshToken refreshToken = RefreshToken.createRefreshToken(member, token.getRefreshToken());
        authRepository.save(refreshToken);
        return token;
    }

    @Override
    public Member signUp(Member member) {
        return memberRepository.save(member);
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    @Override
    public TokenResponse recreateToken(String refreshToken, HttpServletRequest request) {
        //유효성 검사
        try{
            if (!jwtTokenProvider.validateToken(refreshToken)) return null;
        } catch (Exception e) {
            throw new CustomException(ErrorCode.EXPIRED_AUTH_TOKEN);
        }

        Authentication authentication = jwtTokenProvider.getAuthentication(refreshToken, request);
        String memberId = ((UserDetails)authentication.getPrincipal()).getUsername();
        TokenResponse token = jwtTokenProvider.createToken(authentication, memberId);

        Member member = memberRepository.findById(Long.parseLong(memberId)).get();
        RefreshToken rToken = RefreshToken.createRefreshToken(member, token.getRefreshToken());
        authRepository.save(rToken);
        return null;
    }


}