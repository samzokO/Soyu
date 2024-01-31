package com.ssafy.soyu.locker.controller;

import static com.ssafy.soyu.util.response.CommonResponseEntity.getResponseEntity;
import static com.ssafy.soyu.util.response.ErrorResponseEntity.toResponseEntity;

import com.ssafy.soyu.locker.dto.request.ReserveDpDto;
import com.ssafy.soyu.locker.dto.response.LockerListResponse;
import com.ssafy.soyu.locker.service.LockerService;
import com.ssafy.soyu.util.response.ErrorCode;
import com.ssafy.soyu.util.response.SuccessCode;
import com.ssafy.soyu.util.response.exception.CustomException;
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

  //스테이션 id로 보관함 정보 가져오기
  @GetMapping("/list/{stationId}")
  public ResponseEntity<?> getLockers(@PathVariable("stationId") Long stationId) {
    List<LockerListResponse> lockerList = lockerService.getLockers(stationId);
    return getResponseEntity(SuccessCode.OK, lockerList);
  }

  //보관함 상태 확인
  @GetMapping("/{lockerId}")
  public ResponseEntity<?> checkLocker(@PathVariable("lockerId") Long lockerId) {
    lockerService.checkLocker(lockerId);
    return getResponseEntity(SuccessCode.OK);
  }

  @PostMapping("/dp")
  public ResponseEntity<?> reserveToDP(HttpServletRequest request, @RequestBody ReserveDpDto dp) {
    Long memberId = (Long) request.getAttribute("memberId");
    if (memberId == null) {
      throw new CustomException(ErrorCode.USER_NOT_FOUND);
    }
    try {
      lockerService.dpReserve(memberId, dp);
      return getResponseEntity(SuccessCode.OK);
    } catch (CustomException e) {
      return toResponseEntity(e.getErrorCode());
    }
  }

  @PostMapping("/withdraw")
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
