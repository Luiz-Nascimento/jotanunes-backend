package com.jotanunes.especificacoes.dto.ambiente;

import jakarta.validation.constraints.NotNull;


public record AmbienteRequest(
        @NotNull(message = "Id do empreendimento não pode ser nulo.")
        Integer idEmpreendimento,
        @NotNull(message = "Id do catálogo de ambiente não pode ser nulo.")
        Integer idCatalogoAmbiente
) {
}
