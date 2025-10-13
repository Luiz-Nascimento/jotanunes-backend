package com.jotanunes.especificacoes.dto.revisaoItens;

public record RevisaoItemResponse(
        Integer id,
        Integer itemId,
        String status,
        String motivo,
        String emailUsuario,
        String dataAvaliacao
) {
}
