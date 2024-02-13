package com.ssafy.soyu.util.fcm.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.WebpushConfig;
import com.google.firebase.messaging.WebpushNotification;
import com.ssafy.soyu.member.entity.Member;
import com.ssafy.soyu.member.repository.MemberRepository;
import com.ssafy.soyu.util.fcm.entity.Fcm;
import com.ssafy.soyu.util.fcm.dto.FcmMessage;
import com.ssafy.soyu.util.fcm.repository.FcmRepository;
import com.ssafy.soyu.util.response.ErrorCode;
import com.ssafy.soyu.util.response.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FcmService {

  private final FcmRepository fcmRepository;
  private final MemberRepository memberRepository;
  private final FirebaseMessaging firebaseMessaging;

  /**
   * FCM 토큰을 유저에 매칭해서 DB에 저장
   * @param memberId 유저 식별자
   * @param token 등록할 토큰
   */
  public void register(final Long memberId, final String token) {
    Member member = memberRepository.getReferenceById(memberId);

    if(fcmRepository.existsByToken(token))
      throw new CustomException(ErrorCode.ALREADY_REGISTER_TOKEN);

    Fcm fcm = new Fcm(member, token);
    fcmRepository.save(fcm);
  }

  /**
   * FcmMessage 전송
   * @param fcmMessage 메세지 dto
   * @throws FirebaseMessagingException 예외
   */
  public void sendNotice(FcmMessage fcmMessage) throws FirebaseMessagingException {
    Message message = Message.builder()
        .setToken(fcmMessage.getToken())
        .setWebpushConfig(WebpushConfig.builder().putHeader("ttl", "300")
            .setNotification(new WebpushNotification(fcmMessage.getTitle(), fcmMessage.getContent()))
            .build())
        .build();
    firebaseMessaging.send(message);
  }
}