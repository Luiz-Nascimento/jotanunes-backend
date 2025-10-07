package com.jotanunes.especificacoes.dto.CombinacaoEMM;

import java.util.Set;

public record MaterialMarcasResponse(Integer MaterialID,
                                     Set<Integer> marcasID) {
}
