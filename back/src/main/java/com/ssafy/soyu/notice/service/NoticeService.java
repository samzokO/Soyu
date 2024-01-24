package com.ssafy.soyu.notice.service;


import com.google.firebase.messaging.FirebaseMessagingException;
import com.ssafy.soyu.member.domain.Member;
import com.ssafy.soyu.member.repository.MemberRepository;
import com.ssafy.soyu.notice.domain.Notice;
import com.ssafy.soyu.notice.dto.request.NoticeRequestDto;
import com.ssafy.soyu.notice.repository.NoticeRepository;
import com.ssafy.soyu.util.fcm.domain.Fcm;
import com.ssafy.soyu.util.fcm.dto.FcmMessage;
import com.ssafy.soyu.util.fcm.repository.FcmRepository;
import com.ssafy.soyu.util.fcm.service.FcmService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoticeService {

  private final NoticeRepository noticeRepository;
  private final MemberRepository memberRepository;
  private final FcmRepository fcmRepository;
  private final FcmService fcmService;

  /**
   * 알림 생성 및 전송<br/>
   *
   * @param memberId         알림을 받을 유저의 식별자
   * @param noticeRequestDto 알림 정보를 가진 객체 <br/>create notice & send firebase message
   */
  public void createNotice(Long memberId, NoticeRequestDto noticeRequestDto) {
    // 알림 생성하기
    Member receiver = memberRepository.getById(memberId);
    Notice notice = new Notice(receiver, noticeRequestDto);
    noticeRepository.save(notice);

    // 알림 전송을 위한 토큰 조회
    // 유저의 로그인 기기 개수에 따라 Token의 개수가 달라지므로 List 형식으로 조회
    List<Fcm> fcmList = fcmRepository.findAllByMemberId(memberId);
    fcmList.stream()
        .forEach(fcm -> {
          FcmMessage fcmMessage = FcmMessage.builder()
              .token(fcm.getToken())
              .title(noticeRequestDto.getTitle())
              .content(notice.getContent())
              .build();
          try {
            fcmService.sendNotice(fcmMessage);
          } catch (FirebaseMessagingException e) {
            throw new RuntimeException(e);
          }
        });
  }
}
