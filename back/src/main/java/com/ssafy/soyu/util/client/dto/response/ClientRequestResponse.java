package com.ssafy.soyu.util.client.dto.response;

import com.ssafy.soyu.locker.entity.LockerStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientRequestResponse {
    private Long itemId;
    private Integer lockerNum;
    private LockerStatus status;
    private Integer price;
}
