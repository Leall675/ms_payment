package com.desafio.ms_payment.exception;

import com.desafio.ms_payment.dto.error.ErroCampo;
import com.desafio.ms_payment.dto.error.ErroResposta;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResposta> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<ErroCampo> erro = ex.getFieldErrors()
                .stream()
                .map(fieldError -> new ErroCampo(fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());
        ErroResposta erroResposta = new ErroResposta(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Erro de validação", erro);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(erroResposta);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErroResposta> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        ErroResposta erroResposta = new ErroResposta(HttpStatus.BAD_REQUEST.value(), "Erro de validação: verifique os tipos de dados enviados.", List.of());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroResposta);
    }

    @ExceptionHandler(PaymentNotFoundExcemption.class)
    public ResponseEntity<ErroResposta> handlePaymentNotFoundExcemption(PaymentNotFoundExcemption ex) {
        ErroResposta erroResposta = new ErroResposta(HttpStatus.NOT_FOUND.value(), ex.getMessage(), List.of());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erroResposta);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResposta> handleGenericException(Exception ex) {
        ErroResposta resposta = new ErroResposta();
        resposta.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        resposta.setMessage("Ocorreu um erro inesperado. Por favor, tente novamente mais tarde.");
        resposta.setErros(List.of());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resposta);
    }
}
