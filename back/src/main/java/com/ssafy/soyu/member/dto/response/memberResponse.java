package com.ssafy.soyu.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class memberResponse {
  private Long memberId;
  private String nickName;
}