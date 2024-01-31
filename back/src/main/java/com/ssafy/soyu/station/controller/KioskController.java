package com.ssafy.soyu.station.controller;

import static com.ssafy.soyu.util.response.CommonResponseEntity.getResponseEntity;

import com.ssafy.soyu.locker.dto.response.LockerBuyResponse;
import com.ssafy.soyu.locker.service.LockerService;
import com.ssafy.soyu.util.response.CommonResponseEntity;
import com.ssafy.soyu.util.response.SuccessCode;
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
    public ResponseEntity<?> insertSellCode(@PathVariable("code") String code){
        lockerService.insertSellCode(code);
        return getResponseEntity(SuccessCode.OK);
    }

    @GetMapping("/buy/{stationId}/{code}")
    public ResponseEntity<?> insertBuyCode(@PathVariable("stationId") Long stationId, @PathVariable("code") String code){
        LockerBuyResponse response = lockerService.insertBuyCode(stationId, code);
        return getResponseEntity(SuccessCode.OK, response);
    }

    @GetMapping("/withdraw/{code}")
    public ResponseEntity<?> insertWithdrawCode(@PathVariable("code") String code){
        lockerService.insertWithdrawCode(code);
        return getResponseEntity(SuccessCode.OK);
    }

}
