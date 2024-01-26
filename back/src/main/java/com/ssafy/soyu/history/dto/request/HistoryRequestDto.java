package com.ssafy.soyu.history.dto.request;

import lombok.Data;

@Data
public class HistoryRequestDto {
  private Long id;
  private Long itemId;
  private Long memberId;
}
