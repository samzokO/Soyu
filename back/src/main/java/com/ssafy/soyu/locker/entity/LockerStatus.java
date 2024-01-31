package com.ssafy.soyu.locker.entity;

/**
 * AVAILABLE - 사용가능<br/>
 * DP - DP 보관 중<br/>
 * RESERVED - 예약됨(미보관)<br/>
 * WITHDRAW - 회수 대기중<br/>
 * READY - 거래 예약 물품(보관중)<br/>
 */
public enum LockerStatus {
  AVAILABLE , //사용 가능
  DP,         //DP 중
  RESERVED, //예약됨 == 판매자가 안넣음
  WITHDRAW, //회수 대기 중
  READY; // 판매자가 넣음 == 구매자 픽업 대기
}
