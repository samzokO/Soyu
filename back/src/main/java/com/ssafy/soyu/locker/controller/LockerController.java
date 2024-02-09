package com.ssafy.soyu.locker.controller;

import static com.ssafy.soyu.util.response.CommonResponseEntity.getResponseEntity;

import com.ssafy.soyu.locker.dto.response.LockerStationResponse;
import com.ssafy.soyu.locker.entity.Locker;
import com.ssafy.soyu.util.client.dto.request.ReserveDpRequestDto;
import com.ssafy.soyu.locker.service.LockerService;
import com.ssafy.soyu.util.response.SuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/locker")
@Tag(name = "Locker 컨트롤러", description = "Locker 컨트롤러 입니다.")
public class LockerController {

  private final LockerService lockerService;

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

  @PostMapping("/withdraw/{itemId}")
  @Operation(summary = "오프라인 물품 DP 취소", description = "물품 ID를 이용해 DP를 취소합니다. == 회수 예약")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "오프라인 물품 회수 성공"),
      @ApiResponse(responseCode = "400", description = "오프라인 물품 회수 실패")
  })
  public ResponseEntity<?> withdrawItem(HttpServletRequest request, @PathVariable("itemId") Long itemId) {
    Long memberId = (Long) request.getAttribute("memberId");
    lockerService.withdrawReserve(memberId, itemId);
    return getResponseEntity(SuccessCode.OK);
  }

  @PatchMapping("/change/{itemId}")
  @Operation(summary = "거래 예약 물품 DP 전환", description = "물품 ID를 이용해 보관함에 있는 물품을 DP로 전환합니다.")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "DP 전환 성공"),
          @ApiResponse(responseCode = "400", description = "DP 전환 실패")
  })
  public ResponseEntity<?> changeToDP(HttpServletRequest request, @PathVariable("itemId") Long itemId){
    Long memberId = (Long) request.getAttribute("memberId");
    lockerService.changeToDP(memberId, itemId);
    return getResponseEntity(SuccessCode.OK);
  }

  static public LockerStationResponse getLockerStationResponse(Locker locker) {
    if(locker == null) return new LockerStationResponse();
    return new LockerStationResponse(locker.getId(), locker.getStatus(), locker.getLockerNum(), locker.getStation().getId(), locker.getStation().getName());
  }
}
