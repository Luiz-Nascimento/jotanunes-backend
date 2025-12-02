package com.jotanunes.especificacoes.mapper;

import com.jotanunes.especificacoes.dto.item.ItemDocResponse;
import com.jotanunes.especificacoes.dto.item.ItemRequest;
import com.jotanunes.especificacoes.dto.item.ItemResponse;
import com.jotanunes.especificacoes.model.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ItemMapper {

    @Mapping(source = "catalogoItem.nome", target = "nome")
    @Mapping(source = "catalogoItem.descricao", target = "descricao")
    @Mapping(source = "ambiente.id", target = "ambiente")
    ItemResponse toDto(Item item);

    Item toEntity(ItemRequest itemRequest);

    List<ItemResponse> toDtoList(List<Item> items);

    @Mapping(source = "catalogoItem.nome", target = "nome")
    @Mapping(source = "catalogoItem.descricao", target = "descricao")
    ItemDocResponse toDocResponse(Item item);
}
