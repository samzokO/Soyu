package com.ssafy.soyu.station.controller;

import static com.ssafy.soyu.util.response.CommonResponseEntity.getResponseEntity;

import com.ssafy.soyu.item.dto.response.ItemResponse;
import com.ssafy.soyu.locker.dto.response.LockerBuyResponse;
import com.ssafy.soyu.locker.service.LockerService;
import com.ssafy.soyu.util.response.CommonResponseEntity;
import com.ssafy.soyu.util.response.SuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kiosk")
@RequiredArgsConstructor
@Tag(name = "Kiosk 컨트롤러", description = "Kiosk API 입니다")
public class KioskController {
    private final LockerService lockerService;

    @GetMapping("/sell/{code}")
    @Operation(summary = "보관 시작 코드 확인", description = "입력된 코드를 이용해 보관 시작 코드와 일치 여부를 확인합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "보관 시작 코드 일치"),
        @ApiResponse(responseCode = "400", description = "보관 시작 코드 불일치")
    })
    public ResponseEntity<?> insertSellCode(@PathVariable("code") String code){
        lockerService.insertSellCode(code);
        return getResponseEntity(SuccessCode.OK);
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

}
