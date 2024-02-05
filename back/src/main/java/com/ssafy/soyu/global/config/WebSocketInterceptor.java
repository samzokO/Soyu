package com.ssafy.soyu.global.config;

import com.ssafy.soyu.message.dto.request.MessageRequest;
import com.ssafy.soyu.util.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

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
      throw new MessagingException("Authorization header missing");
    }

    String token = authorizationHeader.substring(7);
    boolean ok = jwtTokenProvider.validateToken(token);
    if(!ok){
      throw new MessagingException("Invalid token");
    }

    String memberId = jwtTokenProvider.getSubject(token);
    MessageRequest messageRequest = (MessageRequest) message.getPayload();
    messageRequest.setMemberId(Long.valueOf(memberId)); // memberId 설정

    // 수정한 페이로드를 다시 메시지에 설정합니다. or 헤더에 메시지 추가
    Message<?> newMessage = MessageBuilder.createMessage(messageRequest, headerAccessor.getMessageHeaders());
    return ChannelInterceptor.super.preSend(newMessage, channel);
  }

}
