package com.jotanunes.especificacoes.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class UserAlreadyRegisteredException extends ApiBusinessException {
    public UserAlreadyRegisteredException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
