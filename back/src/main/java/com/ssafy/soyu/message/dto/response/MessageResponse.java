package com.ssafy.soyu.message.dto.response;

import lombok.Data;

@Data
public class MessageResponse {
  Long memberId;
  String content;

  public MessageResponse(Long memberId, String content) {
    this.memberId = memberId;
    this.content = content;
  }
}
