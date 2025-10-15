package com.jotanunes.especificacoes.dto.ambiente;

import com.jotanunes.especificacoes.enums.TipoAmbiente;

public record CatalogoAmbienteRequest(String nome, TipoAmbiente tipo) {
}
