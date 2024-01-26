package com.ssafy.soyu.item.domain.request;

import com.ssafy.soyu.item.domain.ItemStatus;
import lombok.Getter;

@Getter
public class ItemStatusRequest {
  Long itemId;
  ItemStatus itemStatus;
}