package com.ssafy.soyu.util.client.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 오프라인 DP 예약 요청 Dto<br/>
 * lockerId - 보관함 ID<br/>
 * itemId - 물품 ID
 */
@Schema(description = "오프라인 DP 예약 요청 DTO")
@Data
public class ReserveDpRequestDto {

  @Schema(description = "보관함 ID")
  private Long lockerId;

  @Schema(description = "물품 ID")
  private Long itemId;

}
