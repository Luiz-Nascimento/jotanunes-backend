package com.jotanunes.especificacoes.event;

public record UserPasswordResetEvent(
        String email,
        String usuario,
        String senhaTemporaria
) {
}
