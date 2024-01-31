package com.ssafy.soyu.locker.dto.response;

import com.ssafy.soyu.locker.entity.LockerStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class LockerListResponse {
    Long lockerId;
    ItemResponse item;
    String code;
    Boolean isLight;
    Boolean isVisible;
    LockerStatus status;
    Integer location;
    LocalDateTime time;
}
