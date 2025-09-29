package com.jotanunes.especificacoes.mapper;

import com.jotanunes.especificacoes.dto.item.ItemRequest;
import com.jotanunes.especificacoes.dto.item.ItemResponse;
import com.jotanunes.especificacoes.model.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface ItemMapper {
    @Mapping(source = "ambiente.nome", target = "ambiente")
    ItemResponse toDto(Item item);

    Item toEntity(ItemRequest itemRequest);
}
