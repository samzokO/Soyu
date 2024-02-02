package com.ssafy.soyu.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberResponse {
  private Long memberId;
  private String nickName;
}