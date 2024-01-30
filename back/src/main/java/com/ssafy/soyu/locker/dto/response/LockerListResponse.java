package com.ssafy.soyu.locker.dto.response;

import com.ssafy.soyu.locker.LockerStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class LockerListResponse {
    Long lockerId;
    ItemResponse itemResponse;
    String code;
    Boolean isLight;
    Boolean isVisible;
    LockerStatus status;
    String location;
    LocalDateTime time;
}
