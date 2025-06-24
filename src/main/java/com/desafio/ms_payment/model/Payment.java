package com.desafio.ms_payment.model;

import com.desafio.ms_payment.enuns.PaymentEnum;
import com.desafio.ms_payment.enuns.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private Double amount;
    @Enumerated(EnumType.STRING)
    private PaymentEnum paymentMethod;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    @CreationTimestamp
    private LocalDateTime createdAt;

}
