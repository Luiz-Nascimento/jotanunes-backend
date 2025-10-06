package com.jotanunes.especificacoes.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserAlreadyRegisteredException extends RuntimeException {

  public UserAlreadyRegisteredException() {
    super();
  }

  public UserAlreadyRegisteredException(String message) {
    super(message);
  }

  public UserAlreadyRegisteredException(String message, Throwable cause) {
    super(message, cause);
  }

  public UserAlreadyRegisteredException(Throwable cause) {
    super(cause);
  }
}
