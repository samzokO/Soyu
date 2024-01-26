package com.ssafy.soyu.item;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
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
