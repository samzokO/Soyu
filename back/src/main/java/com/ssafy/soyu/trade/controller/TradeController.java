package com.ssafy.soyu.trade.controller;

import static com.ssafy.soyu.util.response.CommonResponseEntity.getResponseEntity;

import com.ssafy.soyu.item.dto.request.DepositInfoRequest;
import com.ssafy.soyu.item.dto.request.ReserveItemRequest;
import com.ssafy.soyu.trade.service.TradeService;
import com.ssafy.soyu.util.payaction.PayActionUtil;
import com.ssafy.soyu.util.response.SuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/trade")
@Tag(name = "Trade 컨트롤러", description = "Trade(거래예약) API 입니다.")
public class TradeController {

  private final TradeService tradeService;
  private final PayActionUtil payActionUtil;

  @PostMapping("/reserve")
  @Operation(summary = "거래예약 생성", description = "ReserveItemRequest를 이용해 거래예약을 생성합니다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "거래예약 생성 성공"),
      @ApiResponse(responseCode = "400", description = "거래예약 생성 실패")
  })
  public ResponseEntity<?> reserveItem(@RequestBody ReserveItemRequest request) {
    tradeService.makeReserve(request.getChatId(), request.getLockerId());
    return getResponseEntity(SuccessCode.OK);
  }

  @GetMapping("/purchase/code/{itemId}")
  @Operation(summary = "거래예약 코드 조회(구매자)", description = "사용자 ID와 아이템 ID를 이용해 거래예약 코드를 조회합니다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "거래예약 코드 조회 성공"),
      @ApiResponse(responseCode = "400", description = "거래예약 코드 조회 실패")
  })
  public ResponseEntity<?> purchaseCode(HttpServletRequest request, @PathVariable("itemId") Long itemId) {
    Long memberId = (Long) request.getAttribute("memberId");

    String code = tradeService.getPurchaseCode(memberId, itemId);
    return getResponseEntity(SuccessCode.OK, code);
  }

  @GetMapping("/sale/code/{itemId}")
  @Operation(summary = "거래예약 코드 조회(판매자)", description = "사용자 ID와 아이템 ID를 이용해 거래예약 코드를 조회합니다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "거래예약 코드 조회 성공"),
      @ApiResponse(responseCode = "400", description = "거래예약 코드 조회 실패")
  })
  public ResponseEntity<?> saleCode(HttpServletRequest request, @PathVariable("itemId") Long itemId) {
    Long memberId = (Long) request.getAttribute("memberId");

    String code = tradeService.getSaleCode(memberId, itemId);
    return getResponseEntity(SuccessCode.OK, code);
  }

  @GetMapping("/withdraw/code/{itemId}")
  @Operation(summary = "회수 코드 조회(판매자)", description = "사용자 ID와 아이템 ID를 이용해 회수 코드를 조회합니다.")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "회수 코드 조회 성공"),
          @ApiResponse(responseCode = "400", description = "회수 코드 조회 실패")
  })
  public ResponseEntity<?> withdrawCode(HttpServletRequest request, @PathVariable("itemId") Long itemId) {
    Long memberId = (Long) request.getAttribute("memberId");

    String code = tradeService.getWithDrawCode(memberId, itemId);
    return getResponseEntity(SuccessCode.OK, code);
  }

  @DeleteMapping("/reserve/{historyId}")
  @Operation(summary = "구매자가 거래 취소", description = "구매내역 ID를 이용해 거래예약을 취소합니다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "거래예약 취소 성공"),
      @ApiResponse(responseCode = "400", description = "거래예약 취소 실패")
  })
  public ResponseEntity<?> deleteBuyReserve(@PathVariable("historyId") Long historyId,
      HttpServletRequest request) {
    Long memberId = (Long) request.getAttribute("memberId");
    tradeService.deleteBuyReserve(historyId, memberId);
    return getResponseEntity(SuccessCode.OK);
  }

  @DeleteMapping("/sale/{itemId}")
  @Operation(summary = "판매자가 거래 취소", description = "아이템 ID를 이용해 거래예약을 취소합니다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "거래 삭제 성공"),
      @ApiResponse(responseCode = "400", description = "거래 삭제 실패")
  })
  public ResponseEntity<?> deleteSaleReserve(HttpServletRequest request, @PathVariable("itemId") Long itemId) {
    Long memberId = (Long) request.getAttribute("memberId");
    tradeService.deleteSaleReserve(memberId, itemId);
    return getResponseEntity(SuccessCode.OK);
  }

  @PostMapping("/match")
  @Operation(summary = "입금 매칭", description = "DepositIndoRequest를 이용해 입금 매칭을 확인합니다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "입금매칭 성공"),
      @ApiResponse(responseCode = "400", description = "입금매칭 실패")
  })
  public ResponseEntity<?> depositMoney(@RequestBody DepositInfoRequest depositInfoRequest) {
    payActionUtil.depositMoney(depositInfoRequest);
    return getResponseEntity(SuccessCode.OK);
  }
}
