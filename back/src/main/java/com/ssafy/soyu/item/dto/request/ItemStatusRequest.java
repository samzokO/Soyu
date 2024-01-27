package com.ssafy.soyu.item.dto.request;

import com.ssafy.soyu.item.entity.ItemStatus;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ItemStatusRequest {
  Long itemId;
  ItemStatus itemStatus;
}