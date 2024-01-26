package com.ssafy.soyu.item.domain.request;

import com.ssafy.soyu.item.domain.ItemCategories;
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
