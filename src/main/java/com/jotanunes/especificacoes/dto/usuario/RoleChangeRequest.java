package com.jotanunes.especificacoes.dto.usuario;

import com.jotanunes.especificacoes.enums.NivelAcesso;
import jakarta.validation.constraints.NotNull;

public record RoleChangeRequest(
        @NotNull(message = "Nível de acesso atualizado é obrigatório")
        NivelAcesso nivelAcesso) {
}
