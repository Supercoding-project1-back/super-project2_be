package com.example.superproject1.web.advice;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private Integer code;
    private String message;
    private String detailMessage;
    private Object request;
}
