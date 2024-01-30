package com.ssafy.soyu.locker.dto.response;

import com.ssafy.soyu.util.soyu.SoyuProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LockerBuyResponse {
    String bankName;
    String accountNumber;
    Integer price;
}
