package com.jotanunes.especificacoes.mapper;

import com.jotanunes.especificacoes.dto.CombinacaoEMM.CombinacaoEMMResponse;
import com.jotanunes.especificacoes.model.CombinacaoEMM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CombinacaoEMMMapper {

    @Mapping(source = "empreendimento.id", target = "empreendimentoID")
    @Mapping(source = "empreendimento.nome", target = "empreendimentoNome")
    @Mapping(source = "material.id", target = "materialID")
    @Mapping(source = "material.nome", target = "materialNome")
    @Mapping(source = "marca.id", target = "marcaID")
    @Mapping(source = "marca.nome", target = "marcaNome")
    CombinacaoEMMResponse toDto(CombinacaoEMM combinacao);

    List<CombinacaoEMMResponse> toDtoList(List<CombinacaoEMM> combinacoes);


}
