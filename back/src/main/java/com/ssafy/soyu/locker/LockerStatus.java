package com.ssafy.soyu.locker;

public enum LockerStatus {
  AVAILABLE , //사용 가능
  DP,         //DP 중
  RESERVED, //예약됨 == 판매자가 안넣음
  WITHDRAW, //회수 대기 중
  READY; // 판매자가 넣음 == 구매자 픽업 대기
}
