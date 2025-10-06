package com.jotanunes.especificacoes.dto.documento;

import com.jotanunes.especificacoes.dto.empreendimento.EmpreendimentoResponse;
import com.jotanunes.especificacoes.enums.DocumentoStatus;

import java.time.LocalDateTime;

public record DocumentoResponse(
        String id,
        EmpreendimentoResponse empreendimento,
        LocalDateTime dataCriacao,
        DocumentoStatus status
) {
}
