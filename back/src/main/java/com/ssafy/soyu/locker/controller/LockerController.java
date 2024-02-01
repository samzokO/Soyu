package com.ssafy.soyu.locker.controller;

import static com.ssafy.soyu.util.response.CommonResponseEntity.getResponseEntity;
import static com.ssafy.soyu.util.response.ErrorResponseEntity.toResponseEntity;

import com.ssafy.soyu.locker.dto.request.ReserveDpRequestDto;
import com.ssafy.soyu.locker.dto.response.LockerListResponse;
import com.ssafy.soyu.locker.service.LockerService;
import com.ssafy.soyu.util.response.ErrorCode;
import com.ssafy.soyu.util.response.SuccessCode;
import com.ssafy.soyu.util.response.exception.CustomException;
import io.swagger.v3.oas.annotations.Operation;
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
  public ResponseEntity<?> getLockers(@PathVariable("stationId") Long stationId) {
    List<LockerListResponse> lockerList = lockerService.getLockers(stationId);
    return getResponseEntity(SuccessCode.OK, lockerList);
  }

  @GetMapping("/{lockerId}")
  @Operation(summary = "보관함 상태 확인", description = "보관함 ID를 이용해 보관함 사용가능 여부를 조회합니다.")
  public ResponseEntity<?> checkLocker(@PathVariable("lockerId") Long lockerId) {
    lockerService.checkLocker(lockerId);
    return getResponseEntity(SuccessCode.OK);
  }

  @PostMapping("/dp")
  @Operation(summary = "오프라인 DP 예약", description = "ReserveDpRequestDto를 이용해 오프라인 DP를 예약합니다.")
  public ResponseEntity<?> reserveToDP(HttpServletRequest request, @RequestBody ReserveDpRequestDto dp) {
    Long memberId = (Long) request.getAttribute("memberId");
    try {
      lockerService.dpReserve(memberId, dp);
      return getResponseEntity(SuccessCode.OK);
    } catch (CustomException e) {
      return toResponseEntity(e.getErrorCode());
    }
  }

  @PostMapping("/withdraw")
  @Operation(summary = "오프라인 물품 회수 예약", description = "물품 ID를 이용해 물품 회수를 신청합니다.")
  public ResponseEntity<?> withdrawItem(HttpServletRequest request, @RequestParam Long itemId) {
    Long memberId = (Long) request.getAttribute("memberId");
    if (memberId == null) {
      throw new CustomException(ErrorCode.USER_NOT_FOUND);
    }

    try {
      lockerService.withdrawReserve(memberId, itemId);
      return getResponseEntity(SuccessCode.OK);
    } catch (CustomException e) {
      return toResponseEntity(e.getErrorCode());
    }
  }
}
