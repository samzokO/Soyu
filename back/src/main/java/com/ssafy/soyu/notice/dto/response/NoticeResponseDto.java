package com.ssafy.soyu.notice.dto.response;

import com.ssafy.soyu.item.entity.Item;
import com.ssafy.soyu.notice.entity.Notice;
import com.ssafy.soyu.notice.entity.NoticeStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "알림 조회 응답 DTO")
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoticeResponseDto {

  @Schema(description = "알림 ID")
  private Long noticeId;

  @Schema(description = "사용자 ID")
  private Long memberId;

  @Schema(description = "알림 상태")
  private NoticeStatus status;

  @Schema(description = "알림 타입")
  private String type;

  @Schema(description = "연관 물품")
  private Item item;

  public NoticeResponseDto(Notice n){
    this.noticeId = n.getId();
    this.memberId = n.getMember().getId();
    this.status = n.getStatus();
    this.type = n.getType();
    this.item = n.getItem();
  }

}
