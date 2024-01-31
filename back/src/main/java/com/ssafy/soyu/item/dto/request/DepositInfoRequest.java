package com.ssafy.soyu.item.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "입금 매칭 요청 DTO")
@Data
public class DepositInfoRequest {

    @Schema(description = "쇼핑몰 ID")
    String mall_id;

    @Schema(description = "주문 번호")
    String order_number;

    @Schema(description = "주문 상태")
    String order_status;

    @Schema(description = "몰라 뭔 일자/시간")
    LocalDateTime processing_date;
}
