package com.ssafy.soyu.util.raspberry.dto.response;

import com.ssafy.soyu.locker.entity.LockerStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RaspberryRequestResponse {
    Long itemId;
    Integer lockerNum;
    LockerStatus status;
    Integer price;
}
