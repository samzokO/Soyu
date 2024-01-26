package com.ssafy.soyu.history.controller;

import com.ssafy.soyu.history.service.HistoryService;
import com.ssafy.soyu.util.response.CommonResponseEntity;
import com.ssafy.soyu.util.response.SuccessCode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/history")
public class HistoryController {

  private final HistoryService historyService;

  /**
   * 구매 내역 조회
   * @param request 사용자의 토큰 정보
   * @return PurchaseResponseDto 구매 내역 조회 정보
   */
  @GetMapping("/purchase")
  public ResponseEntity<?> purchaseHistory(HttpServletRequest request){
    Long memberId = (Long) request.getAttribute("memberId");
    return CommonResponseEntity.getResponseEntity(SuccessCode.OK, historyService.getPurchaseHistory(memberId));
  }

  /**
   * 판매 내역 조회
   * @param request 사용자의 토큰 정보
   * @return SaleResponseDto 판매 내역 조회 정보
   */
  @GetMapping("/sale")
  public ResponseEntity<?> saleHistory(HttpServletRequest request){
    Long memberId = (Long) request.getAttribute("memberId");
    return CommonResponseEntity.getResponseEntity(SuccessCode.OK, historyService.getSaleHistory(memberId));
  }
}
