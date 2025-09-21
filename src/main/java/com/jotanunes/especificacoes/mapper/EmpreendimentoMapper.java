package com.jotanunes.especificacoes.mapper;

import com.jotanunes.especificacoes.dto.empreendimento.EmpreendimentoResponse;
import com.jotanunes.especificacoes.model.Empreendimento;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmpreendimentoMapper {

    EmpreendimentoResponse toDto(Empreendimento empreendimento);
}
