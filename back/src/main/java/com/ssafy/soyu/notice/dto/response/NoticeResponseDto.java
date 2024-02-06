package com.ssafy.soyu.notice.dto.response;

import com.ssafy.soyu.image.entity.Image;
import com.ssafy.soyu.item.entity.Item;
import com.ssafy.soyu.notice.entity.Notice;
import com.ssafy.soyu.notice.entity.NoticeStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "알림 조회 응답 DTO")
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoticeResponseDto {

  @Schema(description = "알림 ID")
  private Long noticeId;

  @Schema(description = "송신인 ID")
  private Long senderId;

  @Schema(description = "송신인 별명")
  private String senderNickName;

  @Schema(description = "알림 상태")
  private NoticeStatus status;

  @Schema(description = "알림 타입")
  private String type;

  @Schema(description = "생성 일자")
  private LocalDateTime localDateTime;

  @Schema(description = "물품 대표이미지")
  private List<Image> image;

  public NoticeResponseDto(Notice n) {
    this.noticeId = n.getId();
    this.status = n.getStatus();
    this.type = n.getType();
    this.localDateTime = n.getRegDate();
    this.image = n.getItem().getImage();
    if(n.getSender() != null){
      this.senderId = n.getSender().getId();
      this.senderNickName = n.getSender().getNickName();
    }
  }
}
