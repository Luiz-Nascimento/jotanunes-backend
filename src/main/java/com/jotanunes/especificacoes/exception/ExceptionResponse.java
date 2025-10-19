package com.jotanunes.especificacoes.exception;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;


public record ExceptionResponse(
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "America/Sao_Paulo")
        Date timestamp,
        String message,
        String details) {}
