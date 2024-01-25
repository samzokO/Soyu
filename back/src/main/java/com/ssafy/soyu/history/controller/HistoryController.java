package com.ssafy.soyu.history.controller;

import com.ssafy.soyu.history.dto.response.PurchaseResponseDto;
import com.ssafy.soyu.history.service.HistoryService;
import com.ssafy.soyu.member.service.MemberService;
import com.ssafy.soyu.util.jwt.JwtTokenProvider;
import com.ssafy.soyu.util.response.CommonResponseEntity;
import com.ssafy.soyu.util.response.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/history")
public class HistoryController {

  private final HistoryService historyService;
  private final MemberService memberService;
  private final JwtTokenProvider jwtTokenProvider;

  /**
   * 구매 내역 조회
   * @param bearerToken 유저의 토큰 정보
   * @return PurchaseResponseDto 구매 내역 조회 정보
   */
  @GetMapping("/purchase")
  public ResponseEntity<?> purchaseHistory(@RequestHeader(value = "Autorization", required = false) String bearerToken){
    Long memberId = jwtTokenProvider.getMemberIdFromToken(memberService.getToken(bearerToken));
    return CommonResponseEntity.getResponseEntity(SuccessCode.OK, historyService.getPurchaseHistory(memberId));
  }
}
