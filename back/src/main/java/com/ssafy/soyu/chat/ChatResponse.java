package com.ssafy.soyu.chat;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ChatResponse {
  Long itemId;

  Long buyerId;

  Long sellerId;

  private String lastMessage;

  private LocalDateTime lastDate;

  private Boolean isChecked;
  private LocalDateTime lastChecked;

}
