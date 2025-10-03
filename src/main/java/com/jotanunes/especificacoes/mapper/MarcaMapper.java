package com.jotanunes.especificacoes.mapper;

import com.jotanunes.especificacoes.dto.marca.MarcaRequest;
import com.jotanunes.especificacoes.dto.marca.MarcaResponse;
import com.jotanunes.especificacoes.model.Marca;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MarcaMapper {

    Marca toEntity(MarcaRequest request);

    MarcaResponse toDTO(Marca marca);
}