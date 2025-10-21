package com.jotanunes.especificacoes.dto.empreendimento;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jotanunes.especificacoes.enums.EmpreendimentoStatus;
import com.jotanunes.especificacoes.enums.LinhaEmpreendimento;
import com.jotanunes.especificacoes.enums.SistemaConstrutivo;
import com.jotanunes.especificacoes.enums.TipologiaEmpreendimento;

import java.util.List;

public record EmpreendimentoResponse(
        Integer id,
        TipologiaEmpreendimento tipologia,
        SistemaConstrutivo sistemaConstrutivo,
        LinhaEmpreendimento linha,
        String nome,
        EmpreendimentoStatus status,
        String localizacao,
        String descricao,
        List<String> observacoes
) {
}
