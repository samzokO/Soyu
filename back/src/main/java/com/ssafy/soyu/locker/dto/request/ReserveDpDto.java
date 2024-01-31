package com.ssafy.soyu.locker.dto.request;

import lombok.Data;

@Data
public class ReserveDpDto {
  private final Long lockerId;
  private final Long itemId;

}
