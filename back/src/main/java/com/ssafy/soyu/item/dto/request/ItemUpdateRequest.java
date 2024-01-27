package com.ssafy.soyu.item.dto.request;

import com.ssafy.soyu.item.entity.ItemCategories;
import lombok.Getter;

@Getter
public class ItemUpdateRequest {
  Long itemId;

  private String title;

  private String content;

  private Integer price;

  private ItemCategories itemCategories;
}
