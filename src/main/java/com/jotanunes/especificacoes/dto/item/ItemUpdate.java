package com.jotanunes.especificacoes.dto.item;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ItemUpdate(
        @NotBlank(message = "Descrição customizada é obrigatória")
        @Size(max = 160, message = "Descrição customizada deve ter no máximo 160 caracteres")
        String descricaoCustomizada) {
}
