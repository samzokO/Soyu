package com.ssafy.soyu.locker.dto.response;

import com.ssafy.soyu.locker.entity.LockerStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LockerStationResponse {
  // 락커점보
  private Long lockerId;
  private LockerStatus status;
  private Integer lockerNum;
  // 스테이션 정보
  private Long stationId;
  private String stationName;
}
