package com.ssafy.soyu.history.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "구매 내역 등록 요청 DTO")
@Data
public class HistoryRequestDto {

  @Schema(description = "물품 ID")
  private Long itemId;

  @Schema(description = "구매자 ID")
  private Long memberId;
}
