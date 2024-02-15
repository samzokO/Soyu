package com.ssafy.soyu.item.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * ONLINE : 온라인<br/>
 * DP_RESERVE : DP 예약 상태<br/>
 * DP : DP 중<br/>
 * SOLD : 판매 완료<br/>
 * DELETED : 삭제됨<br/>
 * TRADE_RESERVE : 온라인 거래 예약중<br/>
 * WITHDRAW : 회수 대기 중<br/>
 */
public enum ItemStatus {
  TRADE_RESERVE,
  DP_RESERVE,
  WITHDRAW,
  ONLINE,
  DP,
  SOLD,
  DELETED;
  @JsonCreator
  public static ItemStatus from(String s) {
    return ItemStatus.valueOf(s.toUpperCase());
  }
}
