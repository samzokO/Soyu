package com.ssafy.soyu.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Schema(description = "닉네임 수정 요청")
@Data
public class MemberNickNameRequest {
  @Schema(description = "닉네임")
  @NotEmpty
  private String nickName;
}
