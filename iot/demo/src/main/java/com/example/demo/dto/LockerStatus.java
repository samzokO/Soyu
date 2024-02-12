package com.example.demo.dto;

/**
 * AVAILABLE - 사용가능
 *
 * [ DP 판매 ]
 * DP_RESERVE       // 박스 자리 배정 상태
 * DP_READY,        // 박스에 물건을 파는중
 * DP_INSERT,       // 박스에 DP 물건을 넣는 중

 * [ 거래 ]
 * TRADE_RESERVE,     // 박스 자리 배정 상태
 * TRADE_READY,      // 박스에 물건 보관 중
 * TRADE_INSERT,     // 박스에 거래 예약 물건을 넣는 중
 * TRADE_CHECK,      // 구매자 코드 입력 후 물건 확인
 *
 * [ 회수 ]
 * WITHDRAW,   // 회수 대기 (DP판매 시간 경과 OR 거래 실패 시 OR 판매자 요청 시)
 *
 * [ 물건빼기 ]
 * SUBTRACT // 물건 빼기
 *
 * [ 물건넣기 ]
 * TRADE_INSERT
 * DP_INSERT
 *
 * [ 라즈베리 파이에만 보내는 RESERVE ]
 * RESERVE
 */
public enum LockerStatus {
  AVAILABLE ,
  DP_READY,
  DP_RESERVE,
  TRADE_RESERVE,
  TRADE_READY,
  TRADE_CHECK,
  WITHDRAW,
  SUBTRACT,
  TRADE_INSERT,
  DP_INSERT,
  RESERVE;
}
