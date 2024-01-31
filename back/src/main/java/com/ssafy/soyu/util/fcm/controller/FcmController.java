package com.ssafy.soyu.util.fcm.controller;

import static com.ssafy.soyu.util.response.ErrorResponseEntity.toResponseEntity;

import com.ssafy.soyu.util.fcm.service.FcmService;
import com.ssafy.soyu.util.response.CommonResponseEntity;
import com.ssafy.soyu.util.response.ErrorCode;
import com.ssafy.soyu.util.response.SuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/fcm")
@Tag(name = "Fcm 컨트롤러", description = "Fcm API 입니다.")
public class FcmController {
  private final FcmService fcmService;

  @PostMapping("")
  @Operation(summary = "FCM 디바이스 토큰 등록", description = "사용자 ID와 디바이스 토큰을 매칭하여 등록합니다.")
  public ResponseEntity<?> registerFcm(HttpServletRequest request, @RequestParam String token){
    Long memberId = (Long) request.getAttribute("memberId");
    if (memberId == null) {
      return toResponseEntity(ErrorCode.USER_NOT_FOUND);
    }

    fcmService.register(memberId, token);

    return CommonResponseEntity.getResponseEntity(SuccessCode.OK);
  }
}
