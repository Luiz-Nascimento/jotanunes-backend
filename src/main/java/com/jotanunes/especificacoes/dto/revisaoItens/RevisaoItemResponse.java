package com.jotanunes.especificacoes.dto.revisaoItens;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public record RevisaoItemResponse(
        Integer id,
        Integer itemId,
        String status,
        String motivo,
        String emailUsuario,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        Date dataAvaliacao
) {
}
