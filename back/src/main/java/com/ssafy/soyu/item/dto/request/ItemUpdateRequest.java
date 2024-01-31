package com.ssafy.soyu.item.dto.request;

import com.ssafy.soyu.item.entity.ItemCategories;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

/**
 * 물품 수정 요청 Dto<br/>
 * title - 제목<br/>
 * content - 설명<br/>
 * price - 가격<br/>
 * itemCategories - 카테고리
 */
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
