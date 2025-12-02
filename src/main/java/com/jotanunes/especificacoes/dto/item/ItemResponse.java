package com.jotanunes.especificacoes.dto.item;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jotanunes.especificacoes.enums.ItemStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ItemResponse(
        Integer id,
        String nome,
        String descricao,
        String descricaoCustomizada,
        ItemStatus status,
        Integer ambiente,
        String motivoReprovacao
) {

    public ItemResponse {
        if (descricaoCustomizada != null && !descricaoCustomizada.isBlank()) {
            descricao = null;
        }
    }
}
