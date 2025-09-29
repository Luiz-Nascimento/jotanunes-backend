package com.jotanunes.especificacoes.dto.item;

import com.jotanunes.especificacoes.enums.ItemStatus;

public record ItemResponse(
        Integer id,
        String nome,
        String descricao,
        ItemStatus status,
        String ambiente
) {
}
