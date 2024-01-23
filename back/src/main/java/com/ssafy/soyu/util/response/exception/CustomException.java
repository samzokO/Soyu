package com.ssafy.soyu.util.response.exception;

import com.ssafy.soyu.util.response.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CustomException extends RuntimeException{
    ErrorCode errorCode;
}