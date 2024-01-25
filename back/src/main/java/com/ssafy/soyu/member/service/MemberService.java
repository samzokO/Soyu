package com.ssafy.soyu.member.service;

import com.ssafy.soyu.member.domain.Member;
import com.ssafy.soyu.member.repository.MemberRepository;
import com.ssafy.soyu.util.jwt.JwtTokenProvider;
import com.ssafy.soyu.util.jwt.domain.RefreshToken;
import com.ssafy.soyu.util.jwt.dto.response.TokenResponse;
import com.ssafy.soyu.util.jwt.repository.AuthRepository;
import com.ssafy.soyu.util.response.ErrorCode;
import com.ssafy.soyu.util.response.exception.CustomException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final AuthRepository authRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public TokenResponse login(Member member) {
        //accessToken, refreshToken 발급
        TokenResponse token = jwtTokenProvider.createToken(member.getId());

        // DB에서 해당 member의 refreshToken을 새 토큰으로 업데이트
        int updatedRows = authRepository.updateRefreshTokenFindByMember(member, token.getRefreshToken());

        // 업데이트된 행이 없다면 새 refreshToken 생성 및 저장 == 회원가입한 유저
        if (updatedRows == 0) {
            RefreshToken newRefreshToken = RefreshToken.createRefreshToken(member, token.getRefreshToken());
            authRepository.save(newRefreshToken);
        }
        return token;
    }

    public Member signUp(Member member) {
        return memberRepository.save(member);
    }

    public Optional<Member> findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    @Transactional
    public TokenResponse recreateToken(String refreshToken) {
        //유효성 검사
        if (!jwtTokenProvider.validateToken(refreshToken)) return null;

        Long memberId = jwtTokenProvider.getMemberIdFromToken(refreshToken);

        // 새로운 토큰 생성
        TokenResponse token = jwtTokenProvider.createToken(memberId);

        // DB에서 refreshToken을 새 토큰으로 업데이트
        int updatedRows = authRepository.updateRefreshTokenFindByToken(refreshToken, token.getRefreshToken());

        // 업데이트된 행이 없다면, 유효하지 않은 refreshToken
        if (updatedRows == 0) {
            throw new CustomException(ErrorCode.INVALID_AUTH_TOKEN);
        }

        return token;
    }

    public String getToken(String bearerToken) {
        String token = null;
        if (bearerToken.startsWith("Bearer"))
            token = bearerToken.substring(7);
        return token;
    }
}