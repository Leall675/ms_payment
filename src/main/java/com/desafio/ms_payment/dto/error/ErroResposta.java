package com.desafio.ms_payment.dto.error;

import com.desafio.ms_payment.dto.error.ErroCampo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErroResposta {
    private long status;
    private String message;
    List<ErroCampo> erros;
}
