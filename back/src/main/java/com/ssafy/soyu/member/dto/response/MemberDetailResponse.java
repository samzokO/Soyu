package com.ssafy.soyu.member.dto.response;

import com.ssafy.soyu.file.ProfileImage;
import com.ssafy.soyu.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MemberDetailResponse {
  private Long memberId;
  private ProfileImage ProfileImage;
  private Long snsId;
  private String email;
  private String nickName;
  private String name;
  private String mobile;
  private String bank_name;
  private String account_number;
  private Boolean isWithdraw;
}
