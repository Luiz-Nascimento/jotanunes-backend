package com.jotanunes.especificacoes.mapper;

import com.jotanunes.especificacoes.dto.ambiente.AmbienteDocResponse;
import com.jotanunes.especificacoes.dto.ambiente.AmbienteRequest;
import com.jotanunes.especificacoes.dto.ambiente.AmbienteResponse;
import com.jotanunes.especificacoes.model.Ambiente;
import com.jotanunes.especificacoes.model.Empreendimento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ItemMapper.class})
public interface AmbienteMapper {
    @Mapping(source = "empreendimento.id", target = "idEmpreendimento")
    AmbienteResponse toDto(Ambiente ambiente);

    AmbienteDocResponse toDocResponse(Ambiente ambiente);

    @Mapping(target = "status", ignore = true)
    Ambiente toEntity(AmbienteRequest ambienteRequest);
}
