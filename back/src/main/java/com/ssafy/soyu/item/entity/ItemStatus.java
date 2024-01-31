package com.ssafy.soyu.item.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * ONLINE : 온라인<br/>
 * DP : DP 중<br/>
 * SOLD : 판매 완료<br/>
 * DELETED : 삭제됨<br/>
 * RESERVE : 예약중<br/>
 * WITHDRAW : 회수 대기 중<br/>
 */
public enum ItemStatus {
  // 차례로 온라인, DP, 판매완료, 삭제됨, 예약중, 회수 대기 중;
  ONLINE, DP, SOLD, DELETED, RESERVE, WITHDRAW;
  @JsonCreator
  public static ItemStatus from(String s) {
    return ItemStatus.valueOf(s.toUpperCase());
  }
}
