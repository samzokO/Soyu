package com.ssafy.soyu.notice.domain;

import com.ssafy.soyu.member.domain.Member;
import com.ssafy.soyu.notice.dto.request.NoticeRequestDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Entity
@Table(name = "notice")
@Getter
public class Notice {
  @Id
  @GeneratedValue
  @Column(name = "notice_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "receiver_id")
  private Member member;

  private String title;
  private String content;

  @Enumerated(EnumType.STRING)
  private NoticeType type;

  @Enumerated(EnumType.STRING)
  private NoticeStatus status;

  @Builder
  public Notice(Member member, NoticeRequestDto noticeRequestDto){
    this.member = member;
    this.title = noticeRequestDto.getTitle();
    this.content = noticeRequestDto.getContent();
    this.type = noticeRequestDto.getNoticeType();
    this.status = NoticeStatus.valueOf("RECEIVE");
  }
}
