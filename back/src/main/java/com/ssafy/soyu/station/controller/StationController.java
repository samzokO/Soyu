package com.ssafy.soyu.station.controller;

import com.ssafy.soyu.station.service.StationService;
import com.ssafy.soyu.util.response.CommonResponseEntity;
import com.ssafy.soyu.util.response.ErrorCode;
import com.ssafy.soyu.util.response.SuccessCode;
import com.ssafy.soyu.util.response.exception.CustomException;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("station")
@RequiredArgsConstructor
@Tag(name = "Station 컨트롤러", description = "Station API 입니다")
public class StationController {

  private final StationService stationService;

  @GetMapping("")
  public ResponseEntity<?> allStation(HttpServletRequest request){
    Long memberId = (Long) request.getAttribute("memberId");
    if(memberId == null) throw new CustomException(ErrorCode.USER_NOT_FOUND);
    return CommonResponseEntity.getResponseEntity(SuccessCode.OK, stationService.findAllStation(memberId));
  }

  @GetMapping("detail")
  public ResponseEntity<?> detailStation(HttpServletRequest request, @RequestParam("stationId") Long stationId){
    Long memberId = (Long) request.getAttribute("memberId");
    if(memberId == null) throw new CustomException(ErrorCode.USER_NOT_FOUND);

    return CommonResponseEntity.getResponseEntity(SuccessCode.OK, stationService.findOneStation(memberId, stationId));
  }


}
