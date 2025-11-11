package com.jotanunes.especificacoes.exception;

public class DocumentGenerationException extends RuntimeException {

    public DocumentGenerationException(String message) {
        super(message);
    }

  public DocumentGenerationException(String message, Throwable cause) {
    super(message, cause);
  }

  public DocumentGenerationException(Throwable cause) {
    super(cause);
  }

  public DocumentGenerationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public DocumentGenerationException() {
  }
}
