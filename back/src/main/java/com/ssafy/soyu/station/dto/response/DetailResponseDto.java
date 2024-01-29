package com.ssafy.soyu.station.dto.response;

import com.ssafy.soyu.item.entity.ItemCategories;
import com.ssafy.soyu.item.entity.ItemStatus;
import com.ssafy.soyu.locker.Locker;
import com.ssafy.soyu.station.domain.Station;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;

@Data
public class DetailResponseDto {

  private Long stationId;
  private String stationName;
  private Boolean isFavorite;
  private List<FindResponseDto> lockers;

  public DetailResponseDto(Station s, List<Locker> ls, boolean isFavorite) {
    this.stationId = s.getId();
    this.stationName = s.getName();
    this.lockers = ls.stream().map(FindResponseDto::new).collect(Collectors.toList());
    this.isFavorite = isFavorite;
  }
}
