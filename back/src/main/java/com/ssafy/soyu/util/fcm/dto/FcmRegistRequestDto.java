package com.ssafy.soyu.util.fcm.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "FCM 등록 요청 DTO")
public class FcmRegistRequestDto {
  String token;
}
