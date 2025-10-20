package com.jotanunes.especificacoes.dto.usuario;

import com.fasterxml.jackson.annotation.JsonInclude;

public record UserResponse(
        String id,
        String nome,
        String email,
        String nivelAcesso,
        Boolean ativo,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String criadoPor
) {}
