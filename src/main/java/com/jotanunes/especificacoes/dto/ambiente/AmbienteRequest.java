package com.jotanunes.especificacoes.dto.ambiente;

import com.jotanunes.especificacoes.enums.TipoAmbiente;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AmbienteRequest(
        @Size(max = 80)
        @NotBlank(message = "Nome do ambiente não pode ser nulo.")
        String nome,
        @NotNull(message = "Tipo do ambiente não pode ser nulo.")
        TipoAmbiente tipo
) {
}
