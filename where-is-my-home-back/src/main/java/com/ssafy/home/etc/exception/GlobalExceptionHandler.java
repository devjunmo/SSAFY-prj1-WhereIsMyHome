package com.ssafy.home.etc.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = BaseException.class)
    public ResponseEntity<String> catchBaseException(BaseException e) {
        return new ResponseEntity<>(e.getMessage(), e.getStatus());
    }
}
