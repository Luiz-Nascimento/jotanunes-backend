package com.jotanunes.especificacoes.exception.handler;

import com.jotanunes.especificacoes.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class CustomEntityResponseHandler {

    @ExceptionHandler(ApiBusinessException.class)
    public final ResponseEntity<ExceptionResponse> handleApiBusinessException(ApiBusinessException ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                new Date(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(exceptionResponse, ex.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> handleAllExceptions(Exception ex, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse(
                new Date(),
                ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<Map<String, String>> handleValidationExceptions(
            MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(
                error -> errors.put(error.getField(), error.getDefaultMessage()));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public final ResponseEntity<ExceptionResponse> handleBadCredentialsException(BadCredentialsException exception,
                                                                                 WebRequest request) {
        ExceptionResponse response = new ExceptionResponse(
                new Date(),
                exception.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public final ResponseEntity<ExceptionResponse> handleMethodArgumentTypeMismatchExceptions(
            MethodArgumentTypeMismatchException exception, WebRequest request) {
        String paramName = exception.getName();
        String requiredType = exception.getRequiredType() != null ? exception.getRequiredType().getSimpleName() : "desconhecido";
        String value = exception.getValue() != null ? exception.getValue().toString() : "null";
        String message = String.format("O parâmetro '%s' deve ser do tipo '%s', mas o valor fornecido foi '%s'", paramName, requiredType, value);

        ExceptionResponse response = new ExceptionResponse(
                new Date(),
                message,
                request.getDescription(false));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public final ResponseEntity<ExceptionResponse> handleHttpMessageNotReadableExceptions(
            HttpMessageNotReadableException exception, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse(
                new Date(),
                "Requisição malformada: " + exception.getMostSpecificCause().getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public final ResponseEntity<ExceptionResponse> handleAuthorizationDeniedException(
            AuthorizationDeniedException exception, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse(
                new Date(),
                exception.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }


}
