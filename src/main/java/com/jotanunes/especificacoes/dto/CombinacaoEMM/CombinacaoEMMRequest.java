package com.jotanunes.especificacoes.dto.CombinacaoEMM;

import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record CombinacaoEMMRequest(
        @NotNull(message = "ID do material é obrigatório")
        Integer materialID,
        @NotNull(message = "IDs das marcas são obrigatórios")
        Set<Integer> marcasID) {
}
