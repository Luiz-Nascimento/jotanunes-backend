package com.jotanunes.especificacoes.mapper;

import com.jotanunes.especificacoes.dto.ambiente.AmbienteRequest;
import com.jotanunes.especificacoes.dto.ambiente.AmbienteResponse;
import com.jotanunes.especificacoes.model.Ambiente;
import com.jotanunes.especificacoes.model.Empreendimento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AmbienteMapper {
    @Mapping(source = "empreendimento.nome", target = "empreendimento")
    AmbienteResponse toDto(Ambiente ambiente);

    @Mapping(target = "id", ignore = true)
    Ambiente toEntity(AmbienteRequest ambienteRequest);
}
