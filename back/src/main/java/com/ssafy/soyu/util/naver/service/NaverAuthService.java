package com.ssafy.soyu.util.naver.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.soyu.member.domain.Member;
import com.ssafy.soyu.util.naver.NaverProperties;
import com.ssafy.soyu.util.naver.NaverProviderProperties;
import com.ssafy.soyu.util.naver.dto.NaverProfile;
import com.ssafy.soyu.util.naver.dto.response.NaverTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class NaverAuthService {
    private final NaverProperties properties;
    private final NaverProviderProperties naverProviderProperties;

    //네이버로부터 accessToken 받기
    public NaverTokenResponse getAccessToken(String code) {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        //properties에 있는 값 넣어주기
        parameters.add("grant_type", properties.getAuthorizationGrantType());
        parameters.add("client_id", properties.getClientId());
        parameters.add("code", code);
        parameters.add("client_secret", properties.getClientSecret());

        //요청 보내고 응답 받기
        String accessTokenUri = naverProviderProperties.getTokenUri();
        WebClient webClient = WebClient.create(accessTokenUri);
        String response = webClient.post()
                .uri(accessTokenUri)
                .bodyValue(parameters)
                .header("Content-type", "application/x-www-form-urlencoded;charset=utf-8")
                .retrieve()
                .bodyToMono(String.class)
                .block();

        // 응답 파싱해서 토큰 반환
        ObjectMapper objectMapper = new ObjectMapper();
        NaverTokenResponse token = null;
        try {
            token = objectMapper.readValue(response, NaverTokenResponse.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return token;
    }

    //네이버에서 받은 accessToken 이용해서 사용자 정보 받아오기
    public NaverProfile getUserInfo(String accessToken) {
        String userInfoUri = naverProviderProperties.getUserInfoUri();
        WebClient webClient = WebClient.create(userInfoUri);
        String response = webClient.post()
                .uri(userInfoUri)
                .header("Authorization", "Bearer " + accessToken)
                .header("Content-type", "application/x-www-form-urlencoded;charset=utf-8")
                .retrieve()
                .bodyToMono(String.class)
                .block();

        // 응답 파싱해서 프로필 정보 가져오기
        ObjectMapper objectMapper = new ObjectMapper();
        NaverProfile profile = null;
        try {
            profile = objectMapper.readValue(response, NaverProfile.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return profile;
    }

    public Member makeNewUser(NaverProfile profile){
       Member newMember = Member.builder()
                .email(profile.getEmail())
                .name(profile.getName())
                .mobile(profile.getMobile())
                .build();
       return newMember;
    }
}
