package com.ssafy.soyu.message.dto.request;
import lombok.Data;

@Data
public class MessageRequest {
  Long chatId;

  Long memberId;

  String content;
}
