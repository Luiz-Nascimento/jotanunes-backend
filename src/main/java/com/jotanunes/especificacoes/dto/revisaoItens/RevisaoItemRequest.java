package com.jotanunes.especificacoes.dto.revisaoItens;

import com.jotanunes.especificacoes.enums.ItemStatus;

public record RevisaoItemRequest(
        ItemStatus status,
        String motivo
) {
}
