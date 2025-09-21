package com.jotanunes.especificacoes.dto.empreendimento;

public record EmpreendimentoRequest(
        String nome,
        String localizacao,
        String descricao,
        String observacao
) {
}
