package com.ssafy.soyu.chat.dto.request;

import lombok.Data;

@Data
public class ChatRequest {
  Long itemId;
  Long buyerId;
  Long sellerId;
}
