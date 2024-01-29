package com.ssafy.soyu.station.dto.response;

import com.ssafy.soyu.item.entity.ItemCategories;
import com.ssafy.soyu.locker.Locker;
import java.time.LocalDateTime;

public class FindResponseDto {
  private Long lockerId;
  private Long itemId;
  private String status;
  private String title;
  private LocalDateTime regDate;
  private Integer price;
  private ItemCategories categories;

  public FindResponseDto(Locker l){
    this.lockerId = l.getId();
    this.itemId = l.getItem().getId();
    this.status = l.getStatus().toString();
    this.title = l.getItem().getTitle();
    this.regDate = l.getItem().getRegDate();
    this.price = l.getItem().getPrice();
    this.categories = l.getItem().getItemCategories();
  }
}
