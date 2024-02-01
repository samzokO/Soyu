package com.ssafy.soyu.locker.dto.response;

import com.ssafy.soyu.locker.entity.LockerStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class KioskLockerResponse {
    private Integer lockerNum;
    private LockerStatus status;
    private LocalDateTime reserveTime;
}
