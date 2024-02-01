package com.ssafy.soyu.locker.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import com.ssafy.soyu.item.dto.response.ItemResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(description = "구매 코드 확인 후 응답 DTO")
@Data
@AllArgsConstructor
public class LockerBuyResponse {

    @Schema(description = "은행 이름")
    String bankName;

    @Schema(description = "계좌 번호")
    String accountNumber;

    @Schema(description = "물품 정보")
    ItemResponse item;
}
