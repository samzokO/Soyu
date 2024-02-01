package com.ssafy.soyu.history.controller;

import static com.ssafy.soyu.util.response.CommonResponseEntity.getResponseEntity;
import static com.ssafy.soyu.util.response.ErrorResponseEntity.toResponseEntity;

import com.ssafy.soyu.history.dto.response.PurchaseResponseDto;
import com.ssafy.soyu.history.dto.response.SaleResponseDto;
import com.ssafy.soyu.history.service.HistoryService;
import com.ssafy.soyu.item.dto.request.DeleteReserveRequest;
import com.ssafy.soyu.item.dto.request.DepositInfoRequest;
import com.ssafy.soyu.util.response.ErrorCode;
import com.ssafy.soyu.util.response.SuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/history")
@Tag(name = "History 컨트롤러", description = "History API 입니다.")
public class HistoryController {

  private final HistoryService historyService;

  @GetMapping("/purchase")
  @Operation(summary = "구매내역 조회", description = "사용자 ID를 이용해 구매내역을 조회합니다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "구매내역 조회 성공", content = @Content(schema = @Schema(implementation = PurchaseResponseDto.class))),
      @ApiResponse(responseCode = "400", description = "구매내역 조회 실패")
  })
  public ResponseEntity<?> purchaseHistory(HttpServletRequest request) {
    Long memberId = (Long) request.getAttribute("memberId");

    return getResponseEntity(SuccessCode.OK, historyService.getPurchaseHistory(memberId));
  }

  @GetMapping("/sale")
  @Operation(summary = "판매내역 조회", description = "사용자 ID를 이용해 판매내역을 조회합니다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "판매내역 조회 성공", content = @Content(schema = @Schema(implementation = SaleResponseDto.class))),
      @ApiResponse(responseCode = "400", description = "판매내역 조회 실패")
  })
  public ResponseEntity<?> saleHistory(HttpServletRequest request) {
    Long memberId = (Long) request.getAttribute("memberId");

    return getResponseEntity(SuccessCode.OK, historyService.getSaleHistory(memberId));
  }

  @DeleteMapping("")
  @Operation(summary = "구매내역 삭제", description = "List<history ID>를 이용해 구매내역을 삭제(soft)합니다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "구매내역 삭제 성공"),
      @ApiResponse(responseCode = "400", description = "구매내역 삭제 실패")
  })
  public ResponseEntity<?> deleteHistory(@RequestParam("historyId") List<Long> historyIdList) {
    if (historyIdList == null || historyIdList.isEmpty()) {
      return toResponseEntity(ErrorCode.EMPTY_REQUEST_VALUE);
    }

    historyService.deleteHistory(historyIdList);
    return getResponseEntity(SuccessCode.OK);
  }
  @DeleteMapping("/sale")
  @Operation(summary = "판매자가 거래 삭제", description = "사용자 ID와 아이템 ID를 이용해 거래예약 코드를 조회합니다.")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "거래 삭제 성공"),
          @ApiResponse(responseCode = "400", description = "거래 삭제 실패")
  })
  public ResponseEntity<?> deleteSaleReserve(HttpServletRequest request, @RequestParam Long itemId) {
    Long memberId = (Long) request.getAttribute("memberId");
    historyService.deleteSaleReserve(memberId, itemId);
    return getResponseEntity(SuccessCode.OK);
  }
}
