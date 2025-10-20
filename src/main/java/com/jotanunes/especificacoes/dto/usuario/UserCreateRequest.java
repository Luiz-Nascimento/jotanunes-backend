package com.jotanunes.especificacoes.dto.usuario;

import com.jotanunes.especificacoes.enums.NivelAcesso;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserCreateRequest(
        @NotBlank(message = "O nome não pode ser vazio")
        String nome,
        @NotBlank(message = "A senha não pode ser vazia")
        String senha,
        @Email(message = "Email inválido")
        String email,
        @NotNull(message = "O nível de acesso não pode ser nulo")
        NivelAcesso nivelAcesso
) {
}
