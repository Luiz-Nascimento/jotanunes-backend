package com.jotanunes.especificacoes.mapper;

import com.jotanunes.especificacoes.dto.usuario.UserCreateRequest;
import com.jotanunes.especificacoes.dto.usuario.UserResponse;
import com.jotanunes.especificacoes.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "criadoPor.nome", target = "criadoPor")
    UserResponse toDto(User user);
    
    User toEntityCreated(UserCreateRequest request);
}
