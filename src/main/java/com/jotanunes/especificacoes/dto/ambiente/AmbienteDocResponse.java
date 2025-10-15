package com.jotanunes.especificacoes.dto.ambiente;

import com.jotanunes.especificacoes.dto.item.ItemDocResponse;
import com.jotanunes.especificacoes.enums.TipoAmbiente;

import java.util.Set;

public record AmbienteDocResponse(String catalogoAmbienteNome, TipoAmbiente catalogoAmbienteTipo, Set<ItemDocResponse> itens) {
}
