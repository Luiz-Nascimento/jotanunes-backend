package com.jotanunes.especificacoes.dto.marca;

import jakarta.validation.constraints.NotBlank;

public record MarcaRequest(
        @NotBlank(message = "O nome da marca é obrigatório")
        String nome
) {
}
