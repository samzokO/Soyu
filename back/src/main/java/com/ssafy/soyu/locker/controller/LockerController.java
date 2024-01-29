package com.ssafy.soyu.locker.controller;

import com.ssafy.soyu.locker.dto.response.LockerListResponse;
import com.ssafy.soyu.locker.service.LockerService;
import com.ssafy.soyu.util.response.CommonResponseEntity;
import com.ssafy.soyu.util.response.SuccessCode;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/locker")
@Tag(name = "Locker 컨트롤러", description = "Locker 컨트롤러 입니다.")
public class LockerController {

    private final LockerService lockerService;

    //스테이션 id로 보관함 정보 가져오기
    @GetMapping("/{stationId}")
    public ResponseEntity<?> getLockers(@PathVariable("stationId") Long stationId){
        List<LockerListResponse> lockerList = lockerService.getLockers(stationId);
        return CommonResponseEntity.getResponseEntity(SuccessCode.OK, lockerList);
    }

    //보관함 상태 확인
    @GetMapping
    public ResponseEntity<?> checkLocker(@PathVariable("lockerId") Long lockerId){
        lockerService.checkLocker(lockerId);
        return CommonResponseEntity.getResponseEntity(SuccessCode.OK);
    }
}
