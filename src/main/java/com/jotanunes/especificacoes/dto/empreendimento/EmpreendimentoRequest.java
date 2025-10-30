package com.jotanunes.especificacoes.dto.empreendimento;

import com.jotanunes.especificacoes.enums.SegmentoEmpreendimento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EmpreendimentoRequest(
        @NotNull(message = "Segmento do empreendimento não pode ser nulo")
        SegmentoEmpreendimento segmento,
        @NotBlank(message = "Nome do empreendimento não pode estar vazio")
        String nome,
        @NotBlank(message = "Localizacao do empreendimento não pode estar vazia")
        String localizacao,
        @NotBlank(message = "Localizacao do empreendimento não pode estar vazia")
        String descricao
) {
}
