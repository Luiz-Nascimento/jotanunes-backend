package com.jotanunes.especificacoes.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EmpreendimentoNotApprovedException extends RuntimeException {
    public EmpreendimentoNotApprovedException(String message) {
        super(message);
    }

  public EmpreendimentoNotApprovedException(String message, Throwable cause) {
    super(message, cause);
  }

  public EmpreendimentoNotApprovedException(Throwable cause) {
    super(cause);
  }

  public EmpreendimentoNotApprovedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public EmpreendimentoNotApprovedException() {
  }
}
