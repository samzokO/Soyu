package com.ssafy.soyu.favorite.dto.response;

import com.ssafy.soyu.favorite.entity.Favorite;
import com.ssafy.soyu.locker.entity.Locker;
import com.ssafy.soyu.station.entity.Station;
import com.ssafy.soyu.station.dto.response.FindResponseDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;

@Data
public class FavoriteListResponseDto {

  private Long favoriteId;
  private Long stationId;
  private String stationName;
  private Float latitude;
  private Float longitude;
  private String address;
  private List<FindResponseDto> lockers;

  public FavoriteListResponseDto(Favorite f, Station s, List<Locker> ls) {
    this.favoriteId = f.getId();
    this.stationId = s.getId();
    this.stationName = s.getName();
    this.latitude = s.getLatitude();
    this.longitude = s.getLongitude();
    this.address = s.getAddress();
    this.lockers = ls.stream().map(FindResponseDto::new).collect(Collectors.toList());
  }
}
