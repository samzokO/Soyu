package com.ssafy.soyu.notice.dto.response;

import com.ssafy.soyu.notice.domain.Notice;
import com.ssafy.soyu.notice.domain.NoticeStatus;
import com.ssafy.soyu.notice.domain.NoticeType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoticeResponseDto {
  private Long noticeId;
  private Long memberId;
  private NoticeStatus status;
  private NoticeType type;

  public NoticeResponseDto(Notice n){
    this.noticeId = n.getId();
    this.memberId = n.getMember().getId();
    this.status = n.getStatus();
    this.type = n.getType();
  }

}
