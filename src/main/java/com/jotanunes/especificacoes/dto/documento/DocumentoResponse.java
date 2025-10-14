package com.jotanunes.especificacoes.dto.documento;

import com.jotanunes.especificacoes.dto.empreendimento.EmpreendimentoDocResponse;
import com.jotanunes.especificacoes.enums.DocumentoStatus;

import java.time.LocalDateTime;

public record DocumentoResponse(
        String id,
        EmpreendimentoDocResponse empreendimento,
        LocalDateTime dataCriacao,
        DocumentoStatus status
) {
}
