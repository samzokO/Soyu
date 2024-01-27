package com.ssafy.soyu.message.dto.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class MessageResponse {
  Long chatId;
  Long memberId;
  String content;

  public MessageResponse(Long chatId, Long memberId, String content) {
    this.chatId = chatId;
    this.memberId = memberId;
    this.content = content;
  }
}
