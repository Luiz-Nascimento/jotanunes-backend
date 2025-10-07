package com.jotanunes.especificacoes.mapper;

import com.jotanunes.especificacoes.dto.CombinacaoEMM.CombinacaoEMMResponse;
import com.jotanunes.especificacoes.model.CombinacaoEMM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CombinacaoEMMMapper {

    @Mapping(source = "empreendimento.id", target = "empreendimentoID")
    @Mapping(source = "material.id", target = "materialID")
    @Mapping(source = "marca.id", target = "marcaID")
    CombinacaoEMMResponse toCombinacaoEMMResponse(CombinacaoEMM combinacao);


}
