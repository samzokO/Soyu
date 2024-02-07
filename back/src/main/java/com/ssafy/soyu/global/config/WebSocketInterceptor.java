package com.ssafy.soyu.global.config;

import com.ssafy.soyu.message.dto.request.MessageRequest;
import com.ssafy.soyu.util.jwt.JwtTokenProvider;
import java.util.Objects;
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
    String sessionId = headerAccessor.getSessionId();

    // 엔드포인트 경로 확인
    String destination = headerAccessor.getDestination();

    // "/stomp/raspberry" 엔드포인트로 들어오는 메시지는 인터셉터 로직을 건너뜁니다.
    if (destination != null && destination.startsWith("/stomp/raspberry")) {
      return message;
    }

    switch ((Objects.requireNonNull(headerAccessor.getCommand()))) {
      case CONNECT:
        // 유저가 Websocket으로 connect()를 한 뒤 호출됨
        log.info("세션 들어옴 => {}", sessionId);
        break;
      case DISCONNECT:
        // 유저가 Websocket으로 disconnect() 를 한 뒤 호출됨 or 세션이 끊어졌을 때 발생
        log.info("세션 끊음 => {}", sessionId);
        break;
    }

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

    Message<?> newMessage = MessageBuilder
        .withPayload(message)
        .setHeader("memberId", Long.valueOf(memberId))
        .build();

    // 수정한 페이로드를 다시 메시지에 설정합니다. or 헤더에 메시지 추가
//    MessageRequest messageRequest = (MessageRequest) message.getPayload();
//    messageRequest.setMemberId(Long.valueOf(memberId)); // memberId 설정
//    Message<?> newMessage = MessageBuilder.createMessage(messageRequest, headerAccessor.getMessageHeaders());
    return ChannelInterceptor.super.preSend(newMessage, channel);
  }

}
