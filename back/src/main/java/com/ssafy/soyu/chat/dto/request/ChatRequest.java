package com.ssafy.soyu.chat.dto.request;

import lombok.Data;

/**
 * 채팅방 생성 요청 DTO<br/>
 * itemId - 아이템 ID<br/>
 * buyerId - 판매자 ID<br/>
 * sellerId - 구매자 ID
 */
@Data
public class ChatRequest {
  Long itemId;
  Long buyerId;
  Long sellerId;
}
