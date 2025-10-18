package com.jotanunes.especificacoes.dto.item;

import jakarta.validation.constraints.NotNull;

public record ItemRequest(
        @NotNull(message = "ID do ambiente é obrigatório")
        Integer idAmbiente,
        @NotNull(message = "ID do item do catálogo é obrigatório")
        Integer idItemCatalogo
) {
}
