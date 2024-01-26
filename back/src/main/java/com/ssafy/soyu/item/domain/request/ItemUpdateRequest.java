package com.ssafy.soyu.item.domain.request;

import com.ssafy.soyu.item.domain.ItemCategories;
import lombok.Getter;

@Getter
public class ItemUpdateRequest {
  Long itemId;

  private String title;

  private String content;

  private Integer price;

  private ItemCategories itemCategories;
}
