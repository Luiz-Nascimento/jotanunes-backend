package com.jotanunes.especificacoes.factory;

import com.jotanunes.especificacoes.dto.usuario.UserCreateRequest;
import com.jotanunes.especificacoes.dto.usuario.UserResponse;
import com.jotanunes.especificacoes.enums.NivelAcesso;
import com.jotanunes.especificacoes.model.User;

import java.util.UUID;

public class UserFactory {

    private UserFactory() {}

    public static UserCreateRequest criarUserCreateRequestPadrao() {
        return new UserCreateRequest("user", "password", "user@gmail.com", NivelAcesso.PADRAO);
    }

    public static User criarUsuarioMapeadoPadrao() {
        return new User("user", "password", "password", NivelAcesso.PADRAO);
    }

    public static User criarUsuarioPersistidoCriadoPadrao() {
        User user = new User();
        user.setId(UUID.fromString("b2ff45dd-f98d-44c3-8f70-eee17f7a8f77"));
        user.setNome("user");
        user.setSenha("$2a$12$m8tsnfw.x7mkVM1nHl1T8e5JtPxW52enFWtug43d2zU/mZxBPDRmC");
        user.setEmail("user@gmail.com");
        user.setNivelAcesso(NivelAcesso.PADRAO);
        user.setCriadoPor(criarUsuarioAdminPadrao());

        return user;
    }

    public static UserResponse criarUserResponsePadrao() {
        return new UserResponse(
                "b2ff45dd-f98d-44c3-8f70-eee17f7a8f77",
                "user",
                "user@gmail.com",
                NivelAcesso.PADRAO.toString(),
                true,
                "admin@gmail.com");

    }
    public static UserResponse criarInativoUserResponsePadrao() {
        return new UserResponse(
                "b2ff45dd-f98d-44c3-8f70-eee17f7a8f77",
                "user",
                "user@gmail.com",
                NivelAcesso.PADRAO.toString(),
                false,
                "admin@gmail.com");

    }
    public static User criarUsuarioAdminPadrao() {
        User user = new User();
        user.setId(UUID.fromString("a1aa45dd-f98d-44c3-8f70-eee17f7a8f11"));
        user.setNivelAcesso(NivelAcesso.ADMIN);
        user.setNome("admin");
        user.setSenha("password");
        user.setEmail("admin@gmail.com");
        return user;
    }
}
