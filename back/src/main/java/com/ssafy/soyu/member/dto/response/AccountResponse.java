package com.ssafy.soyu.member.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Schema(description = "계좌 조회 응답 DTO")
@Data
@Builder
public class AccountResponse {
    @Schema(description = "은행 이름")
    String bankName;

    @Schema(description = "계좌 번호")
    String accountNumber;
}
