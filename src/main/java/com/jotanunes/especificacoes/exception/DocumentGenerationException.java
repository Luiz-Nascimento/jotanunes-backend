package com.jotanunes.especificacoes.exception;

import org.springframework.http.HttpStatus;

public class DocumentGenerationException extends ApiBusinessException {
    public DocumentGenerationException(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
