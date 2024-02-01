package com.ssafy.soyu.item.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;


@Schema(description = "거래 약속 생성 요청 DTO")
@Data
public class ReserveItemRequest {

    @Schema(description = "채팅방 ID")
    Long chatId;

    @Schema(description = "예약하려는 보관함 ID")
    Long lockerId;
}