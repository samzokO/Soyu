package com.ssafy.soyu.item.dto.response;

import com.ssafy.soyu.item.entity.ItemCategories;
import com.ssafy.soyu.item.entity.ItemStatus;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ItemResponse {
  Long itemId;
  Long memberId;
  private String title;

  private String content;

  private LocalDateTime regDate;

  private Integer price;

  private ItemStatus itemStatus;
  private ItemCategories itemCategories;
}
