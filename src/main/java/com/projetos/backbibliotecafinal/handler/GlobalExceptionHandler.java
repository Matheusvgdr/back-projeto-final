package com.projetos.backbibliotecafinal.handler;

import com.projetos.backbibliotecafinal.constants.messages.ValidacoesMessage;
import com.projetos.backbibliotecafinal.dto.response.ApiResponse;
import com.projetos.backbibliotecafinal.handler.exceptions.AutorNaoEncontradoException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        ApiResponse<Map<String, String>> response = new ApiResponse<>();
        response.setData(errors);
        response.setMessage(ValidacoesMessage.ERRO_VALIDACAO);
        response.setHttpStatus(HttpStatus.BAD_REQUEST.value());

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler({AutorNaoEncontradoException.class})
    public ResponseEntity<ApiResponse<?>> handleAutorNaoEncontradoException(AutorNaoEncontradoException e) {
        var resultado = new ApiResponse<>(null, e.getMessage(), HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(resultado.getHttpStatus()).body(resultado);
    }

    @ExceptionHandler({AuthorizationDeniedException.class})
    public ResponseEntity<ApiResponse<?>> handleAuthorizationDeniedException(AuthorizationDeniedException e) {
        var resultado = new ApiResponse<>(null, e.getMessage(), HttpStatus.FORBIDDEN.value());
        return ResponseEntity.status(resultado.getHttpStatus()).body(resultado);
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ApiResponse<?>> handleAuthenticationException(RuntimeException e) {
        var resultado = new ApiResponse<>(null, e.getMessage(), HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(resultado.getHttpStatus()).body(resultado);
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<ApiResponse<?>> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        var resultado = new ApiResponse<>(null, "Um funcionário pode possuir apenas uma biblioteca em sua conta", HttpStatus.FORBIDDEN.value());
        return ResponseEntity.status(resultado.getHttpStatus()).body(resultado);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ApiResponse<?>> handleGenericException(Exception e) {
        var resultado = new ApiResponse<>(null, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.status(resultado.getHttpStatus()).body(resultado);
    }
}
