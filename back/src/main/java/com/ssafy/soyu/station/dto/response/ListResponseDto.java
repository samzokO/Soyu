package com.ssafy.soyu.station.dto.response;

import com.ssafy.soyu.station.entity.Station;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "스테이션 조회 응답 DTO")
@Data
public class ListResponseDto {

  @Schema(description = "스테이션 ID")
  private Long stationId;

  @Schema(description = "스테이션 이름")
  private String name;

  @Schema(description = "주소")
  private String address;

  @Schema(description = "위도")
  private Float latitude;

  @Schema(description = "경도")
  private Float altitude;

  @Schema(description = "사용중인 보관함 개수")
  private Integer fillCount;

  @Schema(description = "즐겨찾기 여부")
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
