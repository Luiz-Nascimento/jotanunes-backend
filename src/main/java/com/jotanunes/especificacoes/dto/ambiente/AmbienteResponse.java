package com.jotanunes.especificacoes.dto.ambiente;

import com.jotanunes.especificacoes.enums.AmbienteStatus;
import com.jotanunes.especificacoes.enums.TipoAmbiente;


public record AmbienteResponse(
        Integer id,
        String catalogoAmbienteNome,
        AmbienteStatus status,
        TipoAmbiente tipoAmbienteCatalogo,
        Integer idEmpreendimento
) {
}
