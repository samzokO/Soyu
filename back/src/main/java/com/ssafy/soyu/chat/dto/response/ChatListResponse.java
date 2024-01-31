package com.ssafy.soyu.chat.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(description = "채팅방 목록 응답 DTO")
@AllArgsConstructor
@Data
public class ChatListResponse {
  @Schema(description = "채팅방 ID")
  Long chatId;

  @Schema(description = "물품 ID")
  Long itemId;

  @Schema(description = "판매자 ID")
  Long buyerId;

  @Schema(description = "구매자 ID")
  Long sellerId;

  @Schema(description = "마지막 메세지 내용")
  private String lastMessage;

  @Schema(description = "마지막 메세지 일자/시간")
  private LocalDateTime lastDate;

  @Schema(description = "마지막 확인 일자/시간")
  private LocalDateTime lastChecked;

  @Schema(description = "확인 여부")
  private Boolean isChecked;
}
