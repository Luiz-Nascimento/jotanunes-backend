package com.jotanunes.especificacoes.dto.marca;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record MarcaRequest(
        @NotBlank(message = "O nome da marca é obrigatório")
        @Size(max = 100, message = "O nome não pode ter mais de 100 caracteres")
        String nome
) {
}
