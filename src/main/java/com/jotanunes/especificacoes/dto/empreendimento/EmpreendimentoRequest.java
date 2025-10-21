package com.jotanunes.especificacoes.dto.empreendimento;

import com.jotanunes.especificacoes.enums.LinhaEmpreendimento;
import com.jotanunes.especificacoes.enums.SistemaConstrutivo;
import com.jotanunes.especificacoes.enums.TipologiaEmpreendimento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EmpreendimentoRequest(
        @NotBlank(message = "Nome do empreendimento não pode estar vazio")
        String nome,
        @NotNull(message = "A tipologia do empreendimento não pode ser nula")
        TipologiaEmpreendimento tipologia,
        @NotNull(message = "O sistema construtivo do empreendimento não pode ser nulo")
        SistemaConstrutivo sistemaConstrutivo,
        @NotNull(message = "A linha do empreendimento não pode ser nula")
        LinhaEmpreendimento linha,
        @NotBlank(message = "Localizacao do empreendimento não pode estar vazia")
        String localizacao,
        @NotBlank(message = "Localizacao do empreendimento não pode estar vazia")
        String descricao
) {
}
