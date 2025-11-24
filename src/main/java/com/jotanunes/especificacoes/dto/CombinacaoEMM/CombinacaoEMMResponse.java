package com.jotanunes.especificacoes.dto.CombinacaoEMM;

public record CombinacaoEMMResponse(
        Integer id,
        Integer empreendimentoID,
        String empreendimentoNome,
        Integer materialID,
        String materialNome,
        Integer marcaID,
        String marcaNome) {}
