package com.jotanunes.especificacoes.dto.item;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CatalogoItemRequest(
        @NotBlank(message = "Nome do item é obrigatório")
        String nome,
        @NotBlank(message = "Descrição do item é obrigatória")
        String descricao,
        @NotNull(message = "ID do ambiente de referência é obrigatório")
        Integer idAmbiente) {
}
