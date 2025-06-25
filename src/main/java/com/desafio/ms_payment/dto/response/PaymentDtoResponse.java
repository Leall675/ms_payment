package com.desafio.ms_payment.dto.response;

import com.desafio.ms_payment.enuns.PaymentEnum;
import com.desafio.ms_payment.enuns.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDtoResponse {
    private String id;
    private Double amount;
    private PaymentEnum paymentMethod;
    private PaymentStatus paymentStatus;
    private LocalDateTime createdAt;
}
