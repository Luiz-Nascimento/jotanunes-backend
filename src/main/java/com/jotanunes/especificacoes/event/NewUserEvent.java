package com.jotanunes.especificacoes.event;

public record NewUserEvent(
        String criadoPor,
        String email,
        String senha
) {
}
