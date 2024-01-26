package com.ssafy.soyu.item.domain.request;

import com.ssafy.soyu.item.domain.ItemStatus;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ItemStatusRequest {
  Long itemId;
  ItemStatus itemStatus;
}