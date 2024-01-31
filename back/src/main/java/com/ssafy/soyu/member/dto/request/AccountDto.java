package com.ssafy.soyu.member.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Schema(description = "계좌 등록/수정 요청 DTO")
@Data
public class AccountDto {

    @Schema(description = "은행 이름")
    @NotEmpty
    String bankName;

    @Schema(description = "계좌 번호")
    @Size(min = 12, max= 14)
    String accountNumber;
}
