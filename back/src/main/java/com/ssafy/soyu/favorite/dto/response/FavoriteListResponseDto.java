package com.ssafy.soyu.favorite.dto.response;

import com.ssafy.soyu.favorite.entity.Favorite;
import com.ssafy.soyu.locker.entity.Locker;
import com.ssafy.soyu.station.entity.Station;
import com.ssafy.soyu.station.dto.response.FindResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;

@Schema(description = "즐겨찾기 목록 조회 응답 DTO")
@Data
public class FavoriteListResponseDto {

  @Schema(description = "즐겨찾기 ID")
  private Long favoriteId;

  @Schema(description = "스테이션 ID")
  private Long stationId;

  @Schema(description = "스테이션 이름")
  private String stationName;

  @Schema(description = "스테이션 위치정보(위도)")
  private Float latitude;

  @Schema(description = "스테이션 위치정보(경도)")
  private Float longitude;

  @Schema(description = "스테이션 위치정보(주소)")
  private String address;

  @Schema(description = "스테이션 내 보관함 정보 목록")
  private List<FindResponseDto> lockers;

  public FavoriteListResponseDto(Favorite f, Station s, List<FindResponseDto> ls) {
    this.favoriteId = f.getId();
    this.stationId = s.getId();
    this.stationName = s.getName();
    this.latitude = s.getLatitude();
    this.longitude = s.getLongitude();
    this.address = s.getAddress();
    this.lockers = ls;
  }
}
