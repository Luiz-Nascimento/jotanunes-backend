package com.jotanunes.especificacoes.mapper;

import com.jotanunes.especificacoes.dto.usuario.UserResponse;
import com.jotanunes.especificacoes.dto.auth.RegisterRequest;
import com.jotanunes.especificacoes.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    UserResponse toDto(Usuario usuario);

    Usuario toEntity(RegisterRequest request);
}
