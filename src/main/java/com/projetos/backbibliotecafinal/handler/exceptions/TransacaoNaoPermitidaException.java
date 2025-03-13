package com.projetos.backbibliotecafinal.handler.exceptions;

public class TransacaoNaoPermitidaException extends RuntimeException {
  public TransacaoNaoPermitidaException(String message) {
    super(message);
  }
}
