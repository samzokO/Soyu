package com.ssafy.soyu.history.controller;

import com.ssafy.soyu.history.service.HistoryService;
import com.ssafy.soyu.util.response.CommonResponseEntity;
import com.ssafy.soyu.util.response.ErrorCode;
import com.ssafy.soyu.util.response.ErrorResponseEntity;
import com.ssafy.soyu.util.response.SuccessCode;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/history")
@Tag(name = "History 컨트롤러", description = "History API 입니다.")
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
    if (memberId == null) {
      return ErrorResponseEntity.toResponseEntity(ErrorCode.USER_NOT_FOUND);
    }
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
    if (memberId == null) {
      return ErrorResponseEntity.toResponseEntity(ErrorCode.USER_NOT_FOUND);
    }
    return CommonResponseEntity.getResponseEntity(SuccessCode.OK, historyService.getSaleHistory(memberId));
  }

  /**
   * 구매 내역 SoftDelete
   * @param historyIdList
   * @return
   */
  @DeleteMapping("")
  public ResponseEntity<?> deleteHistory(@RequestParam(value = "historyId") List<Long> historyIdList){
    if(historyIdList == null || historyIdList.isEmpty())
      return ErrorResponseEntity.toResponseEntity(ErrorCode.EMPTY_REQUEST_VALUE);

    historyService.deleteHistory(historyIdList);
    return CommonResponseEntity.getResponseEntity(SuccessCode.OK);
  }
}
