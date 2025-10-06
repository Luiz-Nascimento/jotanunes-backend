package com.jotanunes.especificacoes.dto.ambiente;

import com.jotanunes.especificacoes.dto.item.ItemResponse;
import com.jotanunes.especificacoes.enums.TipoAmbiente;

import java.util.Set;

public record AmbienteResponse(
        Integer id,
        String nome,
        TipoAmbiente tipo,
        Integer idEmpreendimento,
        Set<ItemResponse> itens
) {
}
