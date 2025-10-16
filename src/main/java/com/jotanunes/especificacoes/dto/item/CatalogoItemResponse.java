package com.jotanunes.especificacoes.dto.item;

public record CatalogoItemResponse(
        Integer id,
        String nome,
        String descricao,
        Integer ambienteId
) {
}
