package com.ssafy.soyu.item.dto.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DepositInfoRequest {
    String mall_id;
    String order_number;
    String order_status;
    LocalDateTime processing_date;
}
