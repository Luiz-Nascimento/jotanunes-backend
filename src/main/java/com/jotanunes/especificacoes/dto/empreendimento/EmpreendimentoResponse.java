package com.jotanunes.especificacoes.dto.empreendimento;

import com.jotanunes.especificacoes.enums.EmpreendimentoStatus;

public record EmpreendimentoResponse(
        Integer id,
        String nome,
        EmpreendimentoStatus status,
        String localizacao,
        String descricao,
        String observacoes
) {
}
