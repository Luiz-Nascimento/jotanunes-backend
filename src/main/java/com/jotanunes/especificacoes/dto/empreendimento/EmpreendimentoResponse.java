package com.jotanunes.especificacoes.dto.empreendimento;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.jotanunes.especificacoes.enums.*;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record EmpreendimentoResponse(
        Integer id,
        SegmentoEmpreendimento segmento,
        String nome,
        EmpreendimentoStatus status,
        String localizacao,
        String descricao,
        List<String> observacoes,
        String criadoPor,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime dataCriacao,
        Integer totalAmbientes,
        Integer totalItens,
        Integer totalMarcas,
        Integer totalMateriais
) {
}
