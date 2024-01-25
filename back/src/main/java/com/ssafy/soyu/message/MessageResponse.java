package com.ssafy.soyu.message;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
