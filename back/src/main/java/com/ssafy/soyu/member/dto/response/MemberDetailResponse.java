package com.ssafy.soyu.member.dto.response;

import com.ssafy.soyu.profileImage.ProfileImage;
import com.ssafy.soyu.profileImage.dto.response.ProfileImageResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MemberDetailResponse {
  private Long memberId;
  private ProfileImageResponse profileImageResponse;
  private Long snsId;
  private String email;
  private String nickName;
  private String name;
  private String mobile;
  private String bank_name;
  private String account_number;
  private Boolean isWithdraw;
}
