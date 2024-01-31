package com.ssafy.soyu.item.dto.request;

import com.ssafy.soyu.item.entity.ItemStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.ToString;

/**
 * 물품 상태 변경 요청 Dto<br/>
 * itemId - 물품 ID<br/>
 * itemStatus - 변경하고자 하는 상태
 */
@Schema(description = "물품 상태 변경 요청 DTO")
@Getter
@ToString
public class ItemStatusRequest {

  @Schema(description = "물품 ID")
  @NotNull
  Long itemId;

  @Schema(description = "변경하고자 하는 상태")
  @NotNull
  ItemStatus itemStatus;
}