package com.ssafy.soyu.chat.dto.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ChatListResponse {
  Long itemId;

  Long buyerId;

  Long sellerId;

  private String lastMessage;

  private LocalDateTime lastDate;

  private Boolean isChecked;
  private LocalDateTime lastChecked;

}
