package com.jotanunes.especificacoes.mapper;

import com.jotanunes.especificacoes.dto.material.MaterialRequest;
import com.jotanunes.especificacoes.dto.material.MaterialResponse;
import com.jotanunes.especificacoes.model.Material;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MaterialMapper {

    Material toEntity(MaterialRequest request);

    MaterialResponse toDTO(Material material);
}