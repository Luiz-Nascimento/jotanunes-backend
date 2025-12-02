package com.jotanunes.especificacoes.exception;

import org.springframework.http.HttpStatus;

public class SendEmailException extends ApiBusinessException {

    public SendEmailException(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
