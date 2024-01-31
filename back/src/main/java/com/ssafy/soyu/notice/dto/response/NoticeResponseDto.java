package com.ssafy.soyu.notice.dto.response;

import com.ssafy.soyu.item.entity.Item;
import com.ssafy.soyu.notice.entity.Notice;
import com.ssafy.soyu.notice.entity.NoticeStatus;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoticeResponseDto {
  private Long noticeId;
  private Long memberId;
  private NoticeStatus status;
  private String type;
  private Item item;

  public NoticeResponseDto(Notice n){
    this.noticeId = n.getId();
    this.memberId = n.getMember().getId();
    this.status = n.getStatus();
    this.type = n.getType();
    this.item = n.getItem();
  }

}
