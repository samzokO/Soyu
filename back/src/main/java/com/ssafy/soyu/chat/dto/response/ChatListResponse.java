package com.ssafy.soyu.chat.dto.response;

import com.ssafy.soyu.image.dto.response.ImageResponse;
import com.ssafy.soyu.image.entity.Image;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Schema(description = "채팅방 목록 응답 DTO")
@AllArgsConstructor
@Data
public class ChatListResponse {
  @Schema(description = "채팅방 ID")
  Long chatId;

  @Schema(description = "물품 ID")
  Long itemId;

  @Schema(description = "물품 사진 list")
  List<ImageResponse> imageResponses;

  @Schema(description = "판매자 ID")
  Long buyerId;

  @Schema(description = "판매자 닉네임")
  String buyerNickname;

  @Schema(description = "구매자 ID")
  Long sellerId;

  @Schema(description = "구매자 닉네임")
  String sellerNickname;

  @Schema(description = "마지막 메세지 내용")
  private String lastMessage;

  @Schema(description = "마지막 메세지 일자/시간")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
  private LocalDateTime lastDate;

  @Schema(description = "마지막 확인 일자/시간")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
  private LocalDateTime lastChecked;

  @Schema(description = "확인 여부")
  private Boolean isChecked;
}
