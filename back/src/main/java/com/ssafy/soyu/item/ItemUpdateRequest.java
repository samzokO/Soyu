package com.ssafy.soyu.item;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ItemUpdateRequest {
  Long itemId;

  private String title;

  private String content;

  private Integer price;

  private ItemCategories itemCategories;
}
