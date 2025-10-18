package com.jotanunes.especificacoes.dto.ambiente;

import com.jotanunes.especificacoes.enums.TipoAmbiente;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CatalogoAmbienteRequest(
        @NotBlank(message = "Nome não pode estar vazio")
        String nome,
        @NotNull(message = "Tipo do ambiente não pode ser nulo")
        TipoAmbiente tipo) {
}
