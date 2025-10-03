package com.jotanunes.especificacoes.dto.ambiente;

import com.jotanunes.especificacoes.enums.TipoAmbiente;

public record AmbienteResponse(
        Integer id,
        String nome,
        TipoAmbiente tipo,
        Integer idEmpreendimento
) {
}
