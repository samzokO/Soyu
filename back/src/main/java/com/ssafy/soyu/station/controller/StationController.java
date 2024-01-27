package com.ssafy.soyu.station.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("station")
@RequiredArgsConstructor
@Tag(name = "Station 컨트롤러", description = "Station API 입니다")
public class StationController {

}
