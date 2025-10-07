package com.jotanunes.especificacoes.dto.CombinacaoEMM;

import java.util.Set;

public record MaterialMarcasIdsResponse(Integer MaterialID,
                                        Set<Integer> marcasID) {
}
