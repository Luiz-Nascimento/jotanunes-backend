package com.jotanunes.especificacoes.dto.empreendimento;

import com.jotanunes.especificacoes.dto.ambiente.AmbienteResponse;

import java.util.Set;

public record EmpreendimentoResponse(
        Integer id,
        String nome,
        String localizacao,
        String descricao,
        String observacoes,
        Set<AmbienteResponse> ambientes
) {
}
