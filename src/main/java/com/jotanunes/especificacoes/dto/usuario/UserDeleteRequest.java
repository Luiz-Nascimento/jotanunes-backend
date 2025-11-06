package com.jotanunes.especificacoes.dto.usuario;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record UserDeleteRequest(
        @NotNull UUID id,
        String motivo
) {
}