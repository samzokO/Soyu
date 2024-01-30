package com.ssafy.soyu.item.dto.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReserveItemRequest {
    Long chatId;
    Long lockerId;
    LocalDateTime reserveTime;

}