package com.ssafy.soyu.notice.dto.request;

import com.ssafy.soyu.item.entity.Item;
import com.ssafy.soyu.notice.domain.NoticeType;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * noticeType = 알림 종류 설정(ENUM)<br/>
 * title = 알림 제목<br/>
 * content = 알림 내용
 */
@Data
@RequiredArgsConstructor
public class NoticeRequestDto {
  private final Item item;
  private final String noticeType;
  private final String title;
  private final String content;

  public NoticeRequestDto(Item item, NoticeType noticeType) {
    this.item = item;
    this.noticeType = noticeType.name();
    this.title = noticeType.getTitle();
    this.content = noticeType.getContent();
  }

  public NoticeRequestDto(Item item,NoticeType noticeType, String code) {
    this.item = item;
    this.noticeType = noticeType.name();
    this.title = noticeType.getTitle();
    this.content = noticeType.getContent() + "\n코드: " + code;
  }
}
