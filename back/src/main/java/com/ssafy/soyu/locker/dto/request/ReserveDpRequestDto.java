package com.ssafy.soyu.locker.dto.request;

import lombok.Data;

/**
 * 오프라인 DP 예약 요청 Dto<br/>
 * lockerId - 보관함 ID<br/>
 * itemId - 물품 ID
 */
@Data
public class ReserveDpRequestDto {
  private final Long lockerId;
  private final Long itemId;

}
