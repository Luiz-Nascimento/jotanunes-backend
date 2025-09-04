package com.jotanunes.especificacoes.mapper;

import com.jotanunes.especificacoes.dto.UsuarioExibirDTO;
import com.jotanunes.especificacoes.model.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    @Mapping(target = "id", expression = "java(usuario.getId().toString())")
    UsuarioExibirDTO toDto(Usuario usuario);
}
