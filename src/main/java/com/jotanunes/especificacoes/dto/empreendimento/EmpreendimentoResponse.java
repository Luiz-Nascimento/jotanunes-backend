package com.jotanunes.especificacoes.dto.empreendimento;

import com.jotanunes.especificacoes.enums.*;

import java.util.List;

public record EmpreendimentoResponse(
        Integer id,
        SegmentoEmpreendimento segmento,
        String nome,
        EmpreendimentoStatus status,
        String localizacao,
        String descricao,
        List<String> observacoes
) {
}
