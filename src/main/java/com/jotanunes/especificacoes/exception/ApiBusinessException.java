package com.jotanunes.especificacoes.exception;

import org.springframework.http.HttpStatus;

public abstract class ApiBusinessException extends RuntimeException {

    private final HttpStatus httpStatus;

    public ApiBusinessException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}