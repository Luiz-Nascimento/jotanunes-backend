package com.jotanunes.especificacoes.mapper;

import com.jotanunes.especificacoes.dto.revisaoItens.RevisaoItemResponse;
import com.jotanunes.especificacoes.model.RevisaoItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RevisaoItemMapper {

    @Mapping(source = "usuario.email", target = "emailUsuario")
    @Mapping(source = "item.id", target = "itemId")
    RevisaoItemResponse toDto(RevisaoItem revisaoItem);
}
