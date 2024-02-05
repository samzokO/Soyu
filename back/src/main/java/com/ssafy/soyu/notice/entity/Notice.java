package com.ssafy.soyu.notice.entity;

import com.ssafy.soyu.item.entity.Item;
import com.ssafy.soyu.member.entity.Member;
import com.ssafy.soyu.notice.dto.request.NoticeRequestDto;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "notice")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notice {

  @Id
  @GeneratedValue
  @Column(name = "notice_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "receiver_id")
  private Member member;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "item_id")
  private Item item;

  private String type;
  private String title;
  private String content;
  private LocalDateTime regDate;

  @Enumerated(EnumType.STRING)
  private NoticeStatus status;

  @Builder
  public Notice(Member member, NoticeRequestDto noticeRequestDto){
    this.member = member;
    this.item = noticeRequestDto.getItem();
    this.title = noticeRequestDto.getTitle();
    this.content = noticeRequestDto.getContent();
    this.type = noticeRequestDto.getNoticeType();
    this.status = NoticeStatus.RECEIVE;
    this.regDate = LocalDateTime.now();
  }
}
