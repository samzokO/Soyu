package com.ssafy.soyu.chat.dto.response;

import com.ssafy.soyu.message.dto.response.MessageResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Schema(description = "채팅방 상세 응답 DTO")
@Data
@AllArgsConstructor
public class ChatResponse {

  @Schema(description = "물품 ID")
  Long itemId;

  @Schema(description = "판매자 ID")
  Long buyerId;

  @Schema(description = "구매자 ID")
  Long sellerId;

  @Schema(description = "마지막 메세지 내용")
  private String lastMessage;

  @Schema(description = "마지막 메세지 일자/시간")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
  private LocalDateTime lastDate;

  @Schema(description = "확인 여부")
  private Boolean isChecked;

  @Schema(description = "마지막 확인 이자/시간")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
  private LocalDateTime lastChecked;

  @Schema(description = "MessageResponse 리스트")
  private List<MessageResponse> messageResponses;
}
