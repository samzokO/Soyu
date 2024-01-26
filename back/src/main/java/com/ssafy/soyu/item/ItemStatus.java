package com.ssafy.soyu.item;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum ItemStatus {
  // 차례로 온라인, DP, 판매완료, 삭제됨;
  online, dp, sold, deleted;
  @JsonCreator
  public static ItemStatus from(String s) {
    return ItemStatus.valueOf(s.toUpperCase());
  }
}
