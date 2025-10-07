package com.jotanunes.especificacoes.mapper;

import com.jotanunes.especificacoes.dto.CombinacaoEMM.MaterialMarcasNomeResponse;
import com.jotanunes.especificacoes.dto.empreendimento.EmpreendimentoRequest;
import com.jotanunes.especificacoes.dto.empreendimento.EmpreendimentoResponse;
import com.jotanunes.especificacoes.model.Empreendimento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", uses = {AmbienteMapper.class})
public interface EmpreendimentoMapper {

    @Mapping(target = "marcas", ignore = true)
    EmpreendimentoResponse toDtoAuto(Empreendimento empreendimento);
    Empreendimento requestToEntity(EmpreendimentoRequest empreendimentoRequest);

    // Método default para preencher o campo manualmente
    default EmpreendimentoResponse toDto(Empreendimento empreendimento, List<MaterialMarcasNomeResponse> marcas) {
        EmpreendimentoResponse auto = toDtoAuto(empreendimento);
        // Cria o record manualmente, reaproveitando os campos automáticos
        return new EmpreendimentoResponse(
                auto.id(),
                auto.nome(),
                auto.localizacao(),
                auto.descricao(),
                auto.observacoes(),
                auto.ambientes(),
                marcas // campo manual
        );
    }
}
