package com.jotanunes.especificacoes.dto.usuario;

import com.jotanunes.especificacoes.enums.NivelAcesso;

public record RoleChangeRequest(NivelAcesso nivelAcesso) {
}
