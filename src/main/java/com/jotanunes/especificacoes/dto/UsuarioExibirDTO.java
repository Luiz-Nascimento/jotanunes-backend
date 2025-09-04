package com.jotanunes.especificacoes.dto;

import java.sql.Timestamp;

public record UsuarioExibirDTO(
        String id,
        String nome,
        String email,
        String perfil,
        Timestamp dataCriacao,
        Boolean ativo
) {
}
