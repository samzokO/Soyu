package com.ssafy.soyu.chat.dto.response;

import com.ssafy.soyu.message.dto.response.MessageResponse;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatResponse {
  Long itemId;

  Long buyerId;

  Long sellerId;

  private String lastMessage;

  private LocalDateTime lastDate;

  private Boolean isChecked;
  private LocalDateTime lastChecked;

  private List<MessageResponse> messageResponses;

}
