package com.jotanunes.especificacoes.dto.documento;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jotanunes.especificacoes.enums.DocumentoStatus;

import java.util.Date;

public record DocumentoResumidoResponse(
        Integer id,
        Integer empreendimentoId,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        Date dataCriacao,
        DocumentoStatus status,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        Date dataAtualizacao
) {}
