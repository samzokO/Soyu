package com.ssafy.soyu.member.service;

import com.ssafy.soyu.member.domain.Member;
import com.ssafy.soyu.member.repository.MemberRepository;
import com.ssafy.soyu.util.jwt.JwtTokenProvider;
import com.ssafy.soyu.util.jwt.dto.response.TokenResponse;
import com.ssafy.soyu.util.naver.dto.NaverProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;


    public TokenResponse login(NaverProfile profile) {

        Long memberId = findOrCreateMember(profile);

        //accessToken, refreshToken 발급
        TokenResponse response = jwtTokenProvider.createToken(memberId);

        Optional<Member> member = memberRepository.findById(memberId);

        //DB에 refreshToken 저장

        return response;
    }


    //가입이 안된 사람이면 회원가입
    private Long findOrCreateMember(NaverProfile profile) {
        return memberRepository.findByEmail(profile.getEmail())
                .map(Member::getId)
                .orElseGet(() -> newMember(profile));
    }


    private Long newMember(NaverProfile profile) {
        Member member = Member.builder()
                .email(profile.getEmail())
                .nickname(profile.getNickname())
                .name(profile.getName())
                .build();

        return memberRepository.save(member).getId();
    }
}