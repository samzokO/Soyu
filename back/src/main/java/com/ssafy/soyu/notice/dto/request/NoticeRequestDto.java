package com.ssafy.soyu.notice.dto.request;

import com.ssafy.soyu.item.entity.Item;
import com.ssafy.soyu.notice.entity.NoticeType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * noticeType = 알림 종류 설정(ENUM)<br/>
 * title = 알림 제목<br/>
 * content = 알림 내용
 */
@Schema(description = "알림 생성 요청 DTO")
@Data
@RequiredArgsConstructor
public class NoticeRequestDto {

  @Schema(description = "연관 물품")
  private Item item;

  @Schema(description = "알림 타입")
  private String noticeType;

  @Schema(description = "제목")
  private String title;

  @Schema(description = "내용")
  private String content;

  public NoticeRequestDto(Item item, NoticeType noticeType) {
    this.item = item;
    this.noticeType = noticeType.name();
    this.title = noticeType.getTitle();
    this.content = noticeType.getContent();
  }

  public NoticeRequestDto(Item item, NoticeType noticeType, String code) {
    this.item = item;
    this.noticeType = noticeType.name();
    this.title = noticeType.getTitle();
    this.content = noticeType.getContent() + "\n코드: " + code;
  }

  public NoticeRequestDto(Item item, NoticeType noticeType, Integer price) {
    this.item = item;
    this.noticeType = noticeType.name();
    this.title = noticeType.getTitle();
    this.content = noticeType.getContent() + "\n할인 후 가격: " + price + "원 입니다.";
  }
}
