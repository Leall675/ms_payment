package com.desafio.ms_payment.controller;

import com.desafio.ms_payment.dto.request.PaymentDtoRequest;
import com.desafio.ms_payment.dto.response.PaymentDtoResponse;
import com.desafio.ms_payment.enuns.PaymentEnum;
import com.desafio.ms_payment.enuns.PaymentStatus;
import com.desafio.ms_payment.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class PaymentControllerTest {

    @InjectMocks
    private PaymentController paymentController;

    @Mock
    private PaymentService paymentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Dado que realizo a criação de um pagamento, então o pagamento deve ser salvo com sucesso.")
    void testSalvar() {
        PaymentDtoRequest dtoRequest = new PaymentDtoRequest();
        dtoRequest.setAmount(99.99);
        dtoRequest.setPaymentMethod(PaymentEnum.PIX);
        dtoRequest.setPaymentStatus(PaymentStatus.PENDENTE);

        PaymentDtoResponse dtoResponse = new PaymentDtoResponse("123", 99.99, PaymentEnum.PIX, PaymentStatus.PENDENTE, null);

        when(paymentService.salvar(dtoRequest)).thenReturn(dtoResponse);

        ResponseEntity<PaymentDtoResponse> response = paymentController.salvar(dtoRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(dtoResponse, response.getBody());
    }

    @Test
    @DisplayName("Dado que o usuário deseja listar todos os pagamentos, então deve retornar a lista de pagamentos.")
    void testListarTodos() {
        PaymentDtoResponse payment1 = new PaymentDtoResponse("1", 10.99, PaymentEnum.PIX, PaymentStatus.PENDENTE, null);
        PaymentDtoResponse payment2 = new PaymentDtoResponse("2", 11.29, PaymentEnum.BOLETO, PaymentStatus.COMPLETO, null);

        List<PaymentDtoResponse> responseList = Arrays.asList(payment1, payment2);

        when(paymentService.listarTodos()).thenReturn(responseList);

        ResponseEntity<List<PaymentDtoResponse>> response = paymentController.listarTodos();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals(payment1, response.getBody().get(0));
        assertEquals(payment2, response.getBody().get(1));
    }

    @Test
    @DisplayName("Dado que o usuário deseja buscar um pagamento por ID, então deve retornar o pagamento correspondente.")
    void testBuscaPorID() {
        PaymentDtoResponse dtoResponse = new PaymentDtoResponse("1", 10.99, PaymentEnum.PIX, PaymentStatus.PENDENTE, null);

        when(paymentService.buscaPorID("1")).thenReturn(dtoResponse);

        ResponseEntity<PaymentDtoResponse> response = paymentController.buscaPorID("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dtoResponse, response.getBody());
    }

    @Test
    @DisplayName("Dado que o usuário deseja excluir um pagamento, então a exclusão deve ser bem-sucedida.")
    void testDeletar() {
        String paymentId = "1";

        ResponseEntity<Void> response = paymentController.deletar(paymentId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}