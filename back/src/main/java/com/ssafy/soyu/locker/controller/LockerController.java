package com.ssafy.soyu.locker.controller;

import static com.ssafy.soyu.util.response.CommonResponseEntity.getResponseEntity;

import com.ssafy.soyu.util.raspberry.dto.request.ReserveDpRequestDto;
import com.ssafy.soyu.locker.dto.response.LockerListResponse;
import com.ssafy.soyu.locker.service.LockerService;
import com.ssafy.soyu.util.response.SuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/locker")
@Tag(name = "Locker 컨트롤러", description = "Locker 컨트롤러 입니다.")
public class LockerController {

  private final LockerService lockerService;

  @GetMapping("/list/{stationId}")
  @Operation(summary = "스테이션 내 보관함 조회", description = "스테이션 ID를 이용해 보관함 정보를 조회합니다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "스테이션 내 보관함 조회 성공", content = @Content(schema = @Schema(implementation = LockerListResponse.class))),
      @ApiResponse(responseCode = "400", description = "스테이션 내 보관함 조회 실패")
  })
  public ResponseEntity<?> getLockers(@PathVariable("stationId") Long stationId) {
    List<LockerListResponse> lockerList = lockerService.getLockers(stationId);
    return getResponseEntity(SuccessCode.OK, lockerList);
  }

  @GetMapping("/{lockerId}")
  @Operation(summary = "보관함 상태 확인", description = "보관함 ID를 이용해 보관함 사용가능 여부를 조회합니다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "보관함 사용 가능"),
      @ApiResponse(responseCode = "400", description = "보관함 사용 불가능")
  })
  public ResponseEntity<?> checkLocker(@PathVariable("lockerId") Long lockerId) {
    lockerService.checkLocker(lockerId);
    return getResponseEntity(SuccessCode.OK);
  }

  @PostMapping("/dp")
  @Operation(summary = "오프라인 DP 예약", description = "ReserveDpRequestDto를 이용해 오프라인 DP를 예약합니다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "오프라인 DP 예약 성공"),
      @ApiResponse(responseCode = "400", description = "오프라인 DP 예약 실패")
  })
  public ResponseEntity<?> reserveToDP(HttpServletRequest request, @RequestBody ReserveDpRequestDto dp) {
    Long memberId = (Long) request.getAttribute("memberId");

    lockerService.dpReserve(memberId, dp);
    return getResponseEntity(SuccessCode.OK);

  }

  @PostMapping("/withdraw")
  @Operation(summary = "오프라인 물품 회수 예약", description = "물품 ID를 이용해 물품 회수를 신청합니다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "오프라인 물품 회수 성공"),
      @ApiResponse(responseCode = "400", description = "오프라인 물품 회수 실패")
  })
  public ResponseEntity<?> withdrawItem(HttpServletRequest request, @RequestParam Long itemId) {
    Long memberId = (Long) request.getAttribute("memberId");

    lockerService.withdrawReserve(memberId, itemId);
    return getResponseEntity(SuccessCode.OK);
  }
}
