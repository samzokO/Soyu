package com.ssafy.soyu.locker.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import com.ssafy.soyu.item.dto.response.ItemResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(description = "구매 코드 확인 후 응답 DTO")
@Data
@AllArgsConstructor
public class LockerBuyResponse {

    @Schema(description = "물품 id")
    Long itemId;

    @Schema(description = "락커 번호")
    Integer LockerNum;
}
