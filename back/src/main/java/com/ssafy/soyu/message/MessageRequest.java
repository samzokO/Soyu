package com.ssafy.soyu.message;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class MessageRequest {
  Long chatId;

  Long memberID;

  String content;
}
