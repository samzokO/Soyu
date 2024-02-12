package com.ssafy.soyu.chat.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
@Schema(description = "채팅방 생성 응답 DTO")
@Data
@AllArgsConstructor
public class ChatIdResponse {
  @Schema(description = "채팅방 ID")
  Long chatId;
}
