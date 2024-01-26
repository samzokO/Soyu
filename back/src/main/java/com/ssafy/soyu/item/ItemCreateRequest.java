package com.ssafy.soyu.item;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ItemCreateRequest {
  Long memberId;

  private String title;

  private String content;

  private LocalDateTime regDate;

  private Integer price;

  private ItemCategories itemCategories;

}
