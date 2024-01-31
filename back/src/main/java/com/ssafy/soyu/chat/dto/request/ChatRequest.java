package com.ssafy.soyu.chat.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 채팅방 생성 요청 DTO<br/>
 * itemId - 아이템 ID<br/>
 * buyerId - 판매자 ID<br/>
 * sellerId - 구매자 ID
 */
@Schema(description = "채팅방 생성 요청 DTO")
@Data
public class ChatRequest {
  @Schema(description = "물품 ID")
  Long itemId;
  @Schema(description = "판매자 ID")
  Long buyerId;
  @Schema(description = "구매자 ID")
  Long sellerId;
}
