package com.ssafy.soyu.item.dto.request;

import com.ssafy.soyu.item.entity.ItemStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ItemStatusRequest {
  @NotNull
  Long itemId;
  @NotNull
  ItemStatus itemStatus;
}