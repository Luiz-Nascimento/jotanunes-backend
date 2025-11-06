package com.jotanunes.especificacoes.dto.empreendimento;

import jakarta.validation.constraints.NotBlank;

public record EmpreendimentoObservacao(
        @NotBlank(message = "A observação não pode estar vazia")
        String observacao
) {
}
