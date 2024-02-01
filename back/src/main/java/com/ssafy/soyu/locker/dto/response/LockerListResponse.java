package com.ssafy.soyu.locker.dto.response;

import com.ssafy.soyu.locker.entity.LockerStatus;
import com.ssafy.soyu.item.dto.response.ItemResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "스테이션 내 보관함 정보 조회 응답 DTO")
@Data
@AllArgsConstructor
public class LockerListResponse {

    @Schema(description = "보관함 ID")
    Long lockerId;

    @Schema(description = "물품 정보")
    ItemResponse item;

    @Schema(description = "암호 코드")
    String code;

    @Schema(description = "조명 상태")
    Boolean isLight;

    @Schema(description = "투명화 상태")
    Boolean isVisible;

    @Schema(description = "보관함 사용 상태")
    LockerStatus status;

    @Schema(description = "보관함 위치")
    Integer location;

    @Schema(description = "시간")
    LocalDateTime time;
}
