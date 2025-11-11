package com.jotanunes.especificacoes.dto.documento;

import org.springframework.http.MediaType;

public record DocumentoGeradoDTO(
        byte[] bytes,
        String filename,
        MediaType contentType
) {
}
