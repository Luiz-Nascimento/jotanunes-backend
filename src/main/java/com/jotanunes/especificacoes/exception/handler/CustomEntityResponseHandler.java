package com.jotanunes.especificacoes.exception.handler;

import com.jotanunes.especificacoes.exception.ExceptionResponse;
import com.jotanunes.especificacoes.exception.ResourceNotFoundException;
import com.jotanunes.especificacoes.exception.UserAlreadyRegisteredException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class CustomEntityResponseHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> handleAllExceptions(Exception ex, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse(
                new Date(),
                ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> handleNotFoundExceptions(ResourceNotFoundException exception, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse(
                new Date(),
                exception.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyRegisteredException.class)
    public final ResponseEntity<ExceptionResponse> handleUserAlreadyFoundExceptions(UserAlreadyRegisteredException exception, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse(
                new Date(),
                exception.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public final ResponseEntity<ExceptionResponse> handleMethodArgumentTypeMismatchExceptions(MethodArgumentTypeMismatchException exception, WebRequest request) {
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

    @ExceptionHandler(NoResourceFoundException.class)
    public final ResponseEntity<ExceptionResponse> handleNoResourceFoundExceptions(NoResourceFoundException exception, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse(
                new Date(),
                "Recurso não encontrado: " + exception.getResourcePath(), "Método : " + exception.getHttpMethod() + " - " +
                request.getDescription(false));
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}
