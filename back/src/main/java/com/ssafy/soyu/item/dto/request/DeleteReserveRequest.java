package com.ssafy.soyu.item.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "거래 예약 취소 요청 DTO")
@Data
public class DeleteReserveRequest {

    @Schema(description = "구매내역 ID")
    Long historyId;
}
