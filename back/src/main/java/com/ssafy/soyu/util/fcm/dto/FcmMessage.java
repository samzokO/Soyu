package com.ssafy.soyu.util.fcm.dto;

import java.util.Map;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FcmMessage {

  private String token;
  private String title;
  private String content;
  private Map<String, String> data;
}
