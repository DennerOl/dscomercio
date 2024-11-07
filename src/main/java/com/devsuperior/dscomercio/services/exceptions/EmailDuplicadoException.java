package com.devsuperior.dscomercio.services.exceptions;

public class EmailDuplicadoException extends RuntimeException {

  public EmailDuplicadoException(String message) {
    super(message);
  }
}
