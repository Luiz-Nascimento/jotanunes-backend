package com.jotanunes.especificacoes.exception;

import org.springframework.http.HttpStatus;

public class EmpreendimentoBusinessLogicException extends ApiBusinessException {
    public EmpreendimentoBusinessLogicException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
