package com.jotanunes.especificacoes.mapper;

import com.jotanunes.especificacoes.dto.revisaoItens.RevisaoItemResponse;
import com.jotanunes.especificacoes.model.RevisaoItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RevisaoItemMapper {

    @Mapping(source = "usuario.email", target = "emailUsuario")
    @Mapping(source = "item.id", target = "itemId")
    @Mapping(source = "item.nome", target = "itemNome")
    @Mapping(source = "item.ambiente.nome", target = "ambienteNome")
    RevisaoItemResponse toDto(RevisaoItem revisaoItem);

    List<RevisaoItemResponse> toDtoList(List<RevisaoItem> revisaoItem);
}
