package com.jotanunes.especificacoes.mapper;

import com.jotanunes.especificacoes.dto.UserResponse;
import com.jotanunes.especificacoes.dto.auth.RegisterRequest;
import com.jotanunes.especificacoes.model.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    UserResponse toDto(Usuario usuario);

    Usuario toEntity(RegisterRequest request);
}
