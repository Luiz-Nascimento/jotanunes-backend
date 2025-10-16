package com.jotanunes.especificacoes.mapper;

import com.jotanunes.especificacoes.dto.item.CatalogoItemRequest;
import com.jotanunes.especificacoes.dto.item.CatalogoItemResponse;
import com.jotanunes.especificacoes.model.CatalogoItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CatalogoItemMapper {

    @Mapping(source = "ambiente.id", target = "ambienteId")
    CatalogoItemResponse toResponse(CatalogoItem catalogoItem);

    CatalogoItem toEntity(CatalogoItemRequest request);
}
