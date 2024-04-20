package com.bordify.exceptions;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ApiExceptionResponse {
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
    private int status;
    private String error;
    private String message;

}
