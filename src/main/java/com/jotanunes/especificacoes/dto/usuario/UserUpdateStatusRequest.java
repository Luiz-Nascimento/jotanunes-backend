package com.jotanunes.especificacoes.dto.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserUpdateStatusRequest(
        @NotBlank(message = "Email do usuário não pode ser vazio")
        String email,
        @NotNull(message = "Novo status do usuário não pode ser nulo")
        Boolean novoStatus
) {
}
