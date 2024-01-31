package com.ssafy.soyu.item.dto.request;

import com.ssafy.soyu.item.entity.ItemStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.ToString;

/**
 * 물품 상태 변경 요청 Dto<br/>
 * itemId - 물품 ID<br/>
 * itemStatus - 변경하고자 하는 상태
 */
@Getter
@ToString
public class ItemStatusRequest {
  @NotNull
  Long itemId;
  @NotNull
  ItemStatus itemStatus;
}