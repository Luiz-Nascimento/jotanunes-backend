package com.jotanunes.especificacoes.mapper;

import com.jotanunes.especificacoes.dto.item.ItemDocResponse;
import com.jotanunes.especificacoes.dto.item.ItemRequest;
import com.jotanunes.especificacoes.dto.item.ItemResponse;
import com.jotanunes.especificacoes.model.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ItemMapper {
    @Mapping(source = "ambiente.id", target = "ambiente")
    ItemResponse toDto(Item item);

    Item toEntity(ItemRequest itemRequest);

    ItemDocResponse toDocResponse(Item item);
}
