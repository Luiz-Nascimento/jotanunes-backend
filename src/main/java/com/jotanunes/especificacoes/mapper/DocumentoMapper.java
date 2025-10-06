package com.jotanunes.especificacoes.mapper;

import com.jotanunes.especificacoes.dto.documento.DocumentoResponse;
import com.jotanunes.especificacoes.model.Documento;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {EmpreendimentoMapper.class})
public interface DocumentoMapper {
    DocumentoResponse toDto(Documento documento);

}
