package com.ssafy.home.etc.exception;

import org.springframework.http.HttpStatus;

public class BaseException extends RuntimeException{
    protected String message;
    protected HttpStatus status;

    public BaseException(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
