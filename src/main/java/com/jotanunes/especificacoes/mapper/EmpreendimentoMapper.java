package com.jotanunes.especificacoes.mapper;

import com.jotanunes.especificacoes.dto.CombinacaoEMM.MaterialMarcasNomeResponse;
import com.jotanunes.especificacoes.dto.empreendimento.EmpreendimentoDocResponse;
import com.jotanunes.especificacoes.dto.empreendimento.EmpreendimentoRequest;
import com.jotanunes.especificacoes.dto.empreendimento.EmpreendimentoResponse;
import com.jotanunes.especificacoes.dto.empreendimento.EmpreendimentoUpdate;
import com.jotanunes.especificacoes.model.Empreendimento;
import org.mapstruct.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.List;


@Mapper(componentModel = "spring", uses = {AmbienteMapper.class})
public interface EmpreendimentoMapper {

    @BeanMapping(
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
    )
    void updateFromDto(EmpreendimentoUpdate dto, @MappingTarget Empreendimento empreendimento);

    @Mapping(source = "criadoPor.nome", target = "criadoPor")
    @Mapping(source = "dataCriacao", target = "dataCriacao", qualifiedByName = "formatar-data")

    EmpreendimentoResponse toDto(Empreendimento empreendimento);

    List<EmpreendimentoResponse> toDtoList(List<Empreendimento> entities);

    Empreendimento requestToEntity(EmpreendimentoRequest empreendimentoRequest);

    EmpreendimentoDocResponse toAutoDocResponse(Empreendimento empreendimento);


    default EmpreendimentoDocResponse toDocResponse(Empreendimento empreendimento, List<MaterialMarcasNomeResponse> marcas) {
        EmpreendimentoDocResponse auto = toAutoDocResponse(empreendimento);
        // Cria o record manualmente, reaproveitando os campos automáticos
        return new EmpreendimentoDocResponse(

                auto.nome(),
                auto.localizacao(),
                auto.descricao(),
                auto.observacoes(),
                auto.ambientes(),
                marcas // campo manual
        );
    }
    @Named("formatar-data")
    default String formatarData(LocalDateTime data) {
        if (data == null) {
            return null;
        }
        return data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }
}
