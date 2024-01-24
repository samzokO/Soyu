package com.ssafy.soyu.member.service;

import com.ssafy.soyu.member.domain.Member;
import com.ssafy.soyu.member.repository.MemberRepository;
import com.ssafy.soyu.util.jwt.JwtTokenProvider;
import com.ssafy.soyu.util.jwt.domain.RefreshToken;
import com.ssafy.soyu.util.jwt.dto.response.TokenResponse;
import com.ssafy.soyu.util.jwt.repository.AuthRepository;
import com.ssafy.soyu.util.response.ErrorCode;
import com.ssafy.soyu.util.response.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final AuthRepository authRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public TokenResponse login(Member member) {
        //accessToken, refreshToken 발급
        TokenResponse token = jwtTokenProvider.createToken(member.getId());

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
    public TokenResponse recreateToken(String refreshToken) {
        //유효성 검사
        try{
            if (!jwtTokenProvider.validateToken(refreshToken)) return null;
        } catch (Exception e) {
            throw new CustomException(ErrorCode.EXPIRED_AUTH_TOKEN);
        }

        Long memberId = Long.parseLong(jwtTokenProvider.getSubject(refreshToken));

        //들어온 refreshToken으로 저장된 값이 없을 때
        RefreshToken rToken = authRepository.findByToken(refreshToken).get();
        if(rToken == null){
            throw new CustomException(ErrorCode.INVALID_AUTH_CODE);
        }

        //db에 있는 토큰과 일치하면 토큰 재발급
        TokenResponse token = jwtTokenProvider.createToken(memberId);
        rToken.updateRefreshToken(token.getRefreshToken());
        authRepository.save(rToken);
        return token;
    }
}