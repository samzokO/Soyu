package com.ssafy.soyu.member.dto.request;

import lombok.Data;

@Data
public class MemberRequest {
  // 네이버에서 제공하는 값은 바꾸지 말자 !
//  private String email;
//  private String nickName;
//  private String name;
//  private String mobile;
  private Long snsId;
  private String bank_name;
  private String account_number;
  private Boolean isWithdraw;
}
