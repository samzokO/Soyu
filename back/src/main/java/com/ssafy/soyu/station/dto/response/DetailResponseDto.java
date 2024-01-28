package com.ssafy.soyu.station.dto.response;

import com.ssafy.soyu.item.entity.ItemCategories;
import com.ssafy.soyu.item.entity.ItemStatus;
import com.ssafy.soyu.locker.Locker;
import com.ssafy.soyu.station.domain.Station;
import java.time.LocalDateTime;

public class DetailResponseDto {

  private Long stationId;
  private String stationName;
  private Long lockerId;
  private Long itemId;
  private String status;
  private String title;
  private LocalDateTime regDate;
  private Integer price;
  private ItemCategories categories;


  public DetailResponseDto(Station s, Locker l, boolean isFavorite) {
    this.stationId = l.getStation().getId();
    this.lockerId = l.getId();
    this.itemId = l.getItem().getId();
    this.stationName = s.getName();
    this.status = l.getStatus().toString();
    this.title = l.getItem().getTitle();
    this.regDate = l.getItem().getRegDate();
    this.price = l.getItem().getPrice();
    this.categories = l.getItem().getItemCategories();
  }
}
