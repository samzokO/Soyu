package com.ssafy.soyu.station.dto.response;

import com.ssafy.soyu.locker.dto.response.LockerResponseDto;
import com.ssafy.soyu.station.entity.Station;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Data;

@Schema(description = "스테이션 세부 조회 응답 DTO")
@Data
public class DetailResponseDto {

  @Schema(description = "스테이션 ID")
  private Long stationId;

  @Schema(description = "스테이션 이름")
  private String stationName;

  @Schema(description = "즐겨찾기 여부")
  private Boolean isFavorite;

  @Schema(description = "보관함 정보")
  private List<LockerResponseDto> lockers;

  public DetailResponseDto(Station s, List<LockerResponseDto> ls, boolean isFavorite) {
    this.stationId = s.getId();
    this.stationName = s.getName();
    this.lockers = ls;
    this.isFavorite = isFavorite;
  }
}
