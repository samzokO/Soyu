package com.ssafy.soyu.global.config;

import com.ssafy.soyu.message.dto.request.MessageRequest;
import com.ssafy.soyu.util.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
@Component
@Slf4j
public class WebSocketInterceptor implements ChannelInterceptor {
  private final JwtTokenProvider jwtTokenProvider;
  @Override
  public Message<?> preSend(Message<?> message, MessageChannel channel) {
    StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);

    // 헤더 토큰 얻기
    String authorizationHeader = String.valueOf(headerAccessor.getNativeHeader("Authorization"));
    if(authorizationHeader == null){
      // 오류 던지기
    }

    String token = authorizationHeader.substring(7);

    boolean ok = jwtTokenProvider.validateToken(token);
    if(!ok){
      // 토큰 유효하지 않다는 오류 던지기
    }

    String memberId = jwtTokenProvider.getSubject(token);

    MessageRequest messageRequest = (MessageRequest) message.getPayload();
    messageRequest.setMemberId(Long.valueOf(memberId)); // memberId 설정

    return ChannelInterceptor.super.preSend(message, channel);
  }

}
