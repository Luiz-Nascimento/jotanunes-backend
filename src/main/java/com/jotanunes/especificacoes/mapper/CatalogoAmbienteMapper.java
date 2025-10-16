package com.jotanunes.especificacoes.mapper;


import com.jotanunes.especificacoes.dto.ambiente.CatalogoAmbienteRequest;
import com.jotanunes.especificacoes.dto.ambiente.CatalogoAmbienteResponse;
import com.jotanunes.especificacoes.model.CatalogoAmbiente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CatalogoAmbienteMapper {

    CatalogoAmbienteResponse toResponse(CatalogoAmbiente catalogoAmbiente);

    CatalogoAmbiente toEntity(CatalogoAmbienteRequest request);
}
