package com.projetos.backbibliotecafinal.handler.exceptions;

public class EmprestimoNaoPermitidoException extends RuntimeException {
  public EmprestimoNaoPermitidoException(String message) {
    super(message);
  }
}
