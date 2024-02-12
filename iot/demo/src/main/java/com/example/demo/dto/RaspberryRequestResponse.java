package com.example.demo.dto;

import lombok.*;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class RaspberryRequestResponse {
    Long itemId;
    Integer lockerNum;
    LockerStatus status;
    Integer price;
}
