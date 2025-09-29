package com.jotanunes.especificacoes.dto.item;

import com.jotanunes.especificacoes.enums.ItemStatus;

public record ItemRequest(
        String nome,
        String descricao
) {
}
