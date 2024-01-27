package com.ssafy.soyu.message.dto.request;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.Getter;

@Data
public class MessageRequest {
  Long chatId;

  Long memberID;

  String content;
}
