package com.ssafy.soyu.chat;

import lombok.Data;

@Data
public class ChatRequest {
  Long itemId;
  Long buyerId;
  Long sellerId;
}
