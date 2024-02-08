package com.ssafy.soyu.util.raspberry.dto.response;

import com.ssafy.soyu.locker.entity.LockerStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RaspberryRequestResponse {
    private Long itemId;
    private Integer lockerNum;
    private LockerStatus status;
    private Integer price;
}
