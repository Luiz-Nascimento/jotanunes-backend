package com.jotanunes.especificacoes.dto.empreendimento;

public record EmpreendimentoUpdate(
        Integer idEmpreendimento,
        String nome,
        String descricao,
        String localizacao) {
}
