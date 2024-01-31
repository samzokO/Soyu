package com.ssafy.soyu.util.fcm.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Schema(description = "FCM 메세지 요청 DTO")
@Data
@Builder
public class FcmMessage {

  @Schema(description = "디바이스 토큰 정보")
  private String token;

  @Schema(description = "FCM 알림 제목")
  private String title;

  @Schema(description = "FCM 알림 내용")
  private String content;
}
