package com.jotanunes.especificacoes.dto.CombinacaoEMM;

import java.util.Set;

public record CombinacaoEMMRequest(Integer materialID, Set<Integer> marcasID) {
}
