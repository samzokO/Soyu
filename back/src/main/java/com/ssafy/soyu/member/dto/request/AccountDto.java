package com.ssafy.soyu.member.dto.request;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AccountDto {
    @NotEmpty
    String bankName;

    @Size(min = 12, max= 14)
    String accountNumber;
}
