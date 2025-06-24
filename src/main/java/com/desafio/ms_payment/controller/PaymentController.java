package com.desafio.ms_payment.controller;

import com.desafio.ms_payment.dto.request.PaymentDtoRequest;
import com.desafio.ms_payment.dto.response.PaymentDtoResponse;
import com.desafio.ms_payment.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
