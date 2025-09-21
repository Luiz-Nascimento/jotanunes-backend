package com.jotanunes.especificacoes.dto.empreendimento;

public record EmpreendimentoResponse(
        Integer id,
        String nome,
        String localizacao,
        String descricao,
        String observacoes
) {
}
