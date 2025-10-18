package com.jotanunes.especificacoes.dto.revisaoItens;

import com.jotanunes.especificacoes.enums.ItemStatus;
import jakarta.validation.constraints.NotNull;

public record RevisaoItemRequest(
        @NotNull(message = "ID do item é obrigatório")
        Integer itemId,
        @NotNull(message = "Novo status do item é obrigatório")
        ItemStatus status,
        String motivo
) {
}
