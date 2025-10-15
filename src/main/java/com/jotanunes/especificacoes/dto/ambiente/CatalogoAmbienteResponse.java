package com.jotanunes.especificacoes.dto.ambiente;

import com.jotanunes.especificacoes.enums.TipoAmbiente;

public record CatalogoAmbienteResponse(Integer id, String nome, TipoAmbiente tipo) {
}
