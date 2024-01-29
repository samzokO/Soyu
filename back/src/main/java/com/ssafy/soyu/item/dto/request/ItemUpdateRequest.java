package com.ssafy.soyu.item.dto.request;

import com.ssafy.soyu.item.entity.ItemCategories;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ItemUpdateRequest {
  @NotNull
  Long itemId;
  @NotNull
  private String title;
  @NotNull
  private String content;
  @NotNull
  private Integer price;
  @NotNull
  private ItemCategories itemCategories;
}
