package com.jotanunes.especificacoes.dto.item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public record ItemDocResponse(
        String nome,
        @JsonIgnore
        String descricao,
        @JsonIgnore
        String descricaoCustomizada) {

    @JsonProperty("descricao")
    public String getDescricaoOutput() {
        if (descricaoCustomizada != null && !descricaoCustomizada.isBlank()) {
            return descricaoCustomizada;
        }
        return descricao;
    }

}
