package com.jotanunes.especificacoes.dto.empreendimento;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EmpreendimentoRequest(
        @NotBlank(message = "Nome do empreendimento não pode estar vazio")
        String nome,
        @NotBlank(message = "Localizacao do empreendimento não pode estar vazia.")
        String localizacao,
        @NotBlank(message = "Localizacao do empreendimento não pode estar vazia.")
        String descricao,
        String observacoes
) {
}
