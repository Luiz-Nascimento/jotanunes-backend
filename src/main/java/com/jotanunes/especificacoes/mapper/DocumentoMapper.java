package com.jotanunes.especificacoes.mapper;

import com.jotanunes.especificacoes.dto.documento.DocumentoResponse;
import com.jotanunes.especificacoes.dto.documento.DocumentoResumidoResponse;
import com.jotanunes.especificacoes.model.Documento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {EmpreendimentoMapper.class})
public interface DocumentoMapper {
    DocumentoResponse toDto(Documento documento);

    @Mapping(source = "empreendimento.id", target = "empreendimentoId")
    DocumentoResumidoResponse toResumidoDto(Documento documento);
}
