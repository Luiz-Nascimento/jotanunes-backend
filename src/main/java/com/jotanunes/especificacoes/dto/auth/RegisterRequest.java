package com.jotanunes.especificacoes.dto.auth;

public record RegisterRequest(
        String nome,
        String senha,
        String email
) {
}
