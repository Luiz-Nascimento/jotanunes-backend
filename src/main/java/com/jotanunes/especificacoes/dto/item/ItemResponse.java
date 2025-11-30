package com.jotanunes.especificacoes.dto.item;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jotanunes.especificacoes.enums.ItemStatus;

public record ItemResponse(
        Integer id,
        String nome,
        String descricao,
        String descricaoCustomizada,
        ItemStatus status,
        Integer ambiente,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String motivoReprovacao
) {
}
