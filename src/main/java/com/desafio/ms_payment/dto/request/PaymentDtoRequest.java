package com.desafio.ms_payment.dto.request;

import com.desafio.ms_payment.enuns.PaymentEnum;
import com.desafio.ms_payment.enuns.PaymentStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class PaymentDtoRequest {
    @NotNull(message = "O valor não pode ser nulo.")
    @Positive(message = "O valor do pagamento deve ser maior que zero.")
    private Double amount;

    @NotNull(message = "O método de pagamento não pode ser nulo. Valores válidos: PIX, CARTAO, BOLETO")
    private PaymentEnum paymentMethod;

    @NotNull(message = "O status do pagamento não pode ser nulo. Valores válidos: PENDENTE, COMPLETO, CANCELADO ")
    private PaymentStatus paymentStatus;

}