package com.jotanunes.especificacoes.dto.ambiente;

import com.jotanunes.especificacoes.enums.TipoAmbiente;

public record AmbienteRequest(
        Integer idEmpreendimento,
        String nome,
        TipoAmbiente tipo
) {
}
