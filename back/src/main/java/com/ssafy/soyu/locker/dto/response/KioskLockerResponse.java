package com.ssafy.soyu.locker.dto.response;

import com.ssafy.soyu.locker.entity.LockerStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class KioskLockerResponse {
    private Integer lockerNum;
    private LockerStatus status;
}
