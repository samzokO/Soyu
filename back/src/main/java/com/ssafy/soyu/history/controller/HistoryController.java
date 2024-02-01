package com.ssafy.soyu.history.controller;

import static com.ssafy.soyu.util.response.CommonResponseEntity.getResponseEntity;
import static com.ssafy.soyu.util.response.ErrorResponseEntity.toResponseEntity;

import com.ssafy.soyu.history.dto.response.PurchaseResponseDto;
import com.ssafy.soyu.history.dto.response.SaleResponseDto;
import com.ssafy.soyu.history.service.HistoryService;
import com.ssafy.soyu.util.response.CommonResponseEntity;
import com.ssafy.soyu.util.response.ErrorCode;
import com.ssafy.soyu.util.response.ErrorResponseEntity;
import com.ssafy.soyu.util.response.SuccessCode;
import com.ssafy.soyu.util.response.exception.CustomException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

  @GetMapping("/purchase")
  @Operation(summary = "구매내역 조회", description = "사용자 ID를 이용해 구매내역을 조회합니다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "구매내역 조회 성공", content = @Content(schema = @Schema(implementation = PurchaseResponseDto.class))),
      @ApiResponse(responseCode = "400", description = "구매내역 조회 실패")
  })
  public ResponseEntity<?> purchaseHistory(HttpServletRequest request) {
    Long memberId = (Long) request.getAttribute("memberId");
    if (memberId == null) {
      return toResponseEntity(ErrorCode.USER_NOT_FOUND);
    }
    return getResponseEntity(SuccessCode.OK,
        historyService.getPurchaseHistory(memberId));
  }

  @GetMapping("/sale")
  @Operation(summary = "판매내역 조회", description = "사용자 ID를 이용해 판매내역을 조회합니다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "판매내역 조회 성공", content = @Content(schema = @Schema(implementation = SaleResponseDto.class))),
      @ApiResponse(responseCode = "400", description = "판매내역 조회 실패")
  })
  public ResponseEntity<?> saleHistory(HttpServletRequest request) {
    Long memberId = (Long) request.getAttribute("memberId");
    if (memberId == null) {
      return toResponseEntity(ErrorCode.USER_NOT_FOUND);
    }
    return getResponseEntity(SuccessCode.OK,
        historyService.getSaleHistory(memberId));
  }

  /**
   * 구매 내역 SoftDelete
   *
   * @param historyIdList
   * @return
   */
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

  @GetMapping("code")
  @Operation(summary = "거래예약 코드 조회", description = "사용자 ID와 아이템 ID를 이용해 거래예약 코드를 조회합니다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "거래예약 코드 조회 성공"),
      @ApiResponse(responseCode = "400", description = "거래예약 코드 조회 실패")
  })
  public ResponseEntity<?> purchaseCode(HttpServletRequest request, @RequestParam Long itemId) {
    Long memberId = (Long) request.getAttribute("memberId");
    if (memberId == null) {
      return toResponseEntity(ErrorCode.USER_NOT_FOUND);
    }

    try {
      //암호가 정상 출력 시
      String code = historyService.getPurchaseCode(memberId, itemId);
      return getResponseEntity(SuccessCode.OK, code);
    } catch (CustomException e) {
      //중간에 예외 발생 시
      return toResponseEntity(e.getErrorCode());
    }
  }
}
