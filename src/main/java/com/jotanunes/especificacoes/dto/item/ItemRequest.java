package com.jotanunes.especificacoes.dto.item;

public record ItemRequest(
        Integer idAmbiente,
        String nome,
        String descricao
) {
}
