package com.desafio.ms_payment.controller;

import com.desafio.ms_payment.dto.request.PaymentDtoRequest;
import com.desafio.ms_payment.dto.response.PaymentDtoResponse;
import com.desafio.ms_payment.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentDtoResponse> salvar(@RequestBody @Valid PaymentDtoRequest dto) {
        PaymentDtoResponse response = paymentService.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<PaymentDtoResponse>> listarTodos() {
        List<PaymentDtoResponse> response = paymentService.listarTodos();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDtoResponse> buscaPorID(@PathVariable String id) {
        PaymentDtoResponse response = paymentService.buscaPorID(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        paymentService.deletar(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
