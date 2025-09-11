package com.jotanunes.especificacoes.dto;

import java.time.LocalDateTime;

public record UsuarioExibirDTO(
        String id,
        String nome,
        String email,
        String perfil,
        LocalDateTime dataCriacao,
        Boolean ativo
) {
}
