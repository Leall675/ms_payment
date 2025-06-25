package com.desafio.ms_payment.service;

import com.desafio.ms_payment.dto.request.PaymentDtoRequest;
import com.desafio.ms_payment.dto.response.PaymentDtoResponse;
import com.desafio.ms_payment.enuns.PaymentEnum;
import com.desafio.ms_payment.enuns.PaymentStatus;
import com.desafio.ms_payment.exception.PaymentNotFoundExcemption;
import com.desafio.ms_payment.mapper.PaymentMapper;
import com.desafio.ms_payment.model.Payment;
import com.desafio.ms_payment.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PaymentServiceTest {

    @InjectMocks
    private PaymentService paymentService;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private PaymentMapper paymentMapper;

    private PaymentDtoRequest dtoRequest;
    private Payment payment;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        dtoRequest = createPaymentDtoRequest(99.99, PaymentEnum.PIX, PaymentStatus.PENDENTE);
        payment = createPayment("123", dtoRequest);
    }

    private PaymentDtoRequest createPaymentDtoRequest(double amount, PaymentEnum paymentMethod, PaymentStatus paymentStatus) {
        PaymentDtoRequest request = new PaymentDtoRequest();
        request.setAmount(amount);
        request.setPaymentMethod(paymentMethod);
        request.setPaymentStatus(paymentStatus);
        return request;
    }

    private Payment createPayment(String id, PaymentDtoRequest request) {
        Payment payment = new Payment();
        payment.setId(id);
        payment.setAmount(request.getAmount());
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setPaymentStatus(request.getPaymentStatus());
        payment.setCreatedAt(LocalDateTime.now());
        return payment;
    }

    @Test
    @DisplayName("Dado que realizo a criacao de um pagamento, Entao o pagamento deve ser salvo com sucesso.")
    void salvar() {
        when(paymentMapper.toPaymentRequest(dtoRequest)).thenReturn(payment);
        when(paymentRepository.save(payment)).thenReturn(payment);
        when(paymentMapper.toPaymentResponse(payment)).thenReturn(new PaymentDtoResponse(payment.getId(), payment.getAmount(), payment.getPaymentMethod(), payment.getPaymentStatus(), payment.getCreatedAt()));

        PaymentDtoResponse response = paymentService.salvar(dtoRequest);

        assertNotNull(response);
        assertEquals(dtoRequest.getAmount(), response.getAmount());
        assertEquals(dtoRequest.getPaymentMethod(), response.getPaymentMethod());
        assertEquals(dtoRequest.getPaymentStatus(), response.getPaymentStatus());
        assertNotNull(response.getCreatedAt());
        verify(paymentRepository).save(any(Payment.class));
    }

    @Test
    @DisplayName("Dado que o usuario deseja realizar uma busca, Entao deve ser retornados todos os pagamentos registrados.")
    void listarTodos() {
        Payment payment1 = createPayment("1", createPaymentDtoRequest(10.99, PaymentEnum.PIX, PaymentStatus.PENDENTE));
        Payment payment2 = createPayment("2", createPaymentDtoRequest(11.29, PaymentEnum.BOLETO, PaymentStatus.COMPLETO));

        List<Payment> paymentList = Arrays.asList(payment1, payment2);

        when(paymentRepository.findAll()).thenReturn(paymentList);
        when(paymentMapper.toPaymentResponse(payment1)).thenReturn(new PaymentDtoResponse(payment1.getId(), payment1.getAmount(), payment1.getPaymentMethod(), payment1.getPaymentStatus(), payment1.getCreatedAt()));
        when(paymentMapper.toPaymentResponse(payment2)).thenReturn(new PaymentDtoResponse(payment2.getId(), payment2.getAmount(), payment2.getPaymentMethod(), payment2.getPaymentStatus(), payment2.getCreatedAt()));

        List<PaymentDtoResponse> responseList = paymentService.listarTodos();

        assertEquals(2, responseList.size());
        verify(paymentRepository).findAll();
    }

    @Test
    @DisplayName("Dado que o usuario deseja realizar uma busca a um pagamento através do ID, Entao deve ser retornado apenas o pagamento atrelado a esse ID")
    void buscaPorID() {
        when(paymentRepository.findById("123")).thenReturn(Optional.of(payment));
        when(paymentMapper.toPaymentResponse(payment)).thenReturn(new PaymentDtoResponse(payment.getId(), payment.getAmount(), payment.getPaymentMethod(), payment.getPaymentStatus(), payment.getCreatedAt()));

        PaymentDtoResponse paymentDtoResponse = paymentService.buscaPorID("123");

        assertNotNull(paymentDtoResponse);
        assertEquals("123", paymentDtoResponse.getId());
        verify(paymentRepository).findById("123");
    }

    @Test
    @DisplayName("Dado que o usuario realize uma busca por um ID de pagamento inválido, Entao deve ser retornado a exceção de PaymentNotFoundExcemption")
    public void testBuscaPorID_NotFound() {
        when(paymentRepository.findById("123")).thenReturn(Optional.empty());
        assertThrows(PaymentNotFoundExcemption.class, () -> paymentService.buscaPorID("123"));
    }

    @Test
    @DisplayName("Dado que o usuario deseja excluir um registro de pagamento, Entao o pagamento excluido deve ser removido da base de dados")
    void deletar() {
        when(paymentRepository.findById("123")).thenReturn(Optional.of(payment));

        paymentService.deletar("123");

        verify(paymentRepository).deleteById("123");
    }
}