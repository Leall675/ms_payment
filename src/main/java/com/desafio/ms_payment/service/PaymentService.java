package com.desafio.ms_payment.service;

import com.desafio.ms_payment.dto.request.PaymentDtoRequest;
import com.desafio.ms_payment.dto.response.PaymentDtoResponse;
import com.desafio.ms_payment.mapper.PaymentMapper;
import com.desafio.ms_payment.model.Payment;
import com.desafio.ms_payment.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentMapper paymentMapper;

    public PaymentDtoResponse salvar(PaymentDtoRequest paymentDtoRequest) {
        Payment payment =  paymentMapper.toPaymentRequest(paymentDtoRequest);

        Payment savedPayment = paymentRepository.save(payment);
        return paymentMapper.toPaymentResponse(savedPayment);
    }

    public List<PaymentDtoResponse> listarTodos() {
        List<Payment> paymentList = paymentRepository.findAll();
        return paymentList.stream().map(payment -> paymentMapper.toPaymentResponse(payment)).toList();
    }

}
