package com.ssafy.soyu.station.controller;

import static com.ssafy.soyu.util.response.CommonResponseEntity.getResponseEntity;

import com.ssafy.soyu.locker.dto.response.LockerBuyResponse;
import com.ssafy.soyu.locker.service.LockerService;
import com.ssafy.soyu.station.dto.response.HistoryIdResponseDto;
import com.ssafy.soyu.util.response.SuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/kiosk")
@RequiredArgsConstructor
@Tag(name = "Kiosk 컨트롤러", description = "Kiosk API 입니다")
public class KioskController {
    private final LockerService lockerService;

    @GetMapping("/sell/{code}")
    @Operation(summary = "DP/거래예약 보관 시작 코드 확인", description = "입력된 코드를 이용해 보관 시작 코드와 일치 여부를 확인합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "보관 시작 코드 일치"),
        @ApiResponse(responseCode = "400", description = "보관 시작 코드 불일치")
    })
    public ResponseEntity<?> insertSellCode(@PathVariable("code") String code){
        return getResponseEntity(SuccessCode.OK, lockerService.insertSellCode(code));
    }

    @GetMapping("/buy/{stationId}/{code}")
    @Operation(summary = "구매 코드 확인", description = "입력된 코드를 이용해 구매 코드와 일치 여부를 확인합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "구매 코드 일치"),
        @ApiResponse(responseCode = "400", description = "구매 코드 불일치")
    })
    public ResponseEntity<?> insertBuyCode(@PathVariable("stationId") Long stationId, @PathVariable("code") String code){
        LockerBuyResponse response = lockerService.insertBuyCode(stationId, code);
        return getResponseEntity(SuccessCode.OK, response);
    }

    @GetMapping("/withdraw/{code}")
    @Operation(summary = "회수 코드 확인", description = "입력된 코드를 이용해 회수 코드와 일치 여부를 확인합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "회수 코드 일치"),
        @ApiResponse(responseCode = "400", description = "회수 코드 불일치")
    })
    public ResponseEntity<?> insertWithdrawCode(@PathVariable("code") String code){
        lockerService.insertWithdrawCode(code);
        return getResponseEntity(SuccessCode.OK);
    }

    @GetMapping("/reserve")
    @Operation(summary = "거래 물품 구매 결정", description = "거래 물품 구매를 결정했는지 안했는지 확인합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
    })
    public ResponseEntity<?> reserveBuyDecision(@RequestParam("isBuy") boolean isBuy, @RequestParam("itemId") Long itemId){
        lockerService.reserveBuyDecision(isBuy, itemId);
        return getResponseEntity(SuccessCode.OK);
    }

    @GetMapping("/dp")
    @Operation(summary = "DP 물품 구매 결정", description = "DP 물품 구매 결정에서 YES를 입력했을 때만 실행됩니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
    })
    public ResponseEntity<?> dpBuyDecision(@RequestParam("itemId") Long itemId){
        Long historyId = lockerService.dpBuyDecision(itemId);
        HistoryIdResponseDto responseDto = new HistoryIdResponseDto();
        responseDto.setHistoryId(historyId);
        return getResponseEntity(SuccessCode.OK, responseDto);
    }
}
