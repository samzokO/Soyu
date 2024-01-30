package com.ssafy.soyu.station.dto.response;

import com.ssafy.soyu.station.domain.Station;
import lombok.Data;

@Data
public class ListResponseDto {
  private Long stationId;
  private String name;
  private String address;
  private Float latitude;
  private Float altitude;
  private Integer fillCount;
  private boolean isFavorite;

  public ListResponseDto(Station s, Integer count, boolean isFavorite) {
    this.stationId = s.getId();
    this.name = s.getName();
    this.address = s.getAddress();
    this.latitude = s.getLatitude();
    this.altitude = s.getLongitude();
    this.isFavorite = isFavorite;
    this.fillCount = count;
  }
}
