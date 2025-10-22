package com.jotanunes.especificacoes.service;


import com.jotanunes.especificacoes.dto.usuario.UserCreateRequest;
import com.jotanunes.especificacoes.dto.usuario.UserResponse;
import com.jotanunes.especificacoes.dto.usuario.UserUpdateStatusRequest;
import com.jotanunes.especificacoes.factory.UserFactory;
import com.jotanunes.especificacoes.mapper.UserMapper;
import com.jotanunes.especificacoes.model.User;
import com.jotanunes.especificacoes.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private SecurityContext securityContextMock;

    @Mock
    private Authentication authenticationMock;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    public void deveDesativarUsuario() {
        UserUpdateStatusRequest data = new UserUpdateStatusRequest("user@gmail.com", Boolean.FALSE);
        User usuarioEncontrado = UserFactory.criarUsuarioPersistidoCriadoPadrao();
        User usuarioAtualizado = UserFactory.criarUsuarioPersistidoCriadoPadrao();
        usuarioAtualizado.setAtivo(Boolean.FALSE);
        UserResponse expectedResponse = UserFactory.criarInativoUserResponsePadrao();

        when(userRepository.findByEmail(data.email())).thenReturn(Optional.of(usuarioEncontrado));
        when(userRepository.save(usuarioEncontrado)).thenReturn(usuarioAtualizado);
        when(userMapper.toDto(usuarioAtualizado)).thenReturn(expectedResponse);

        UserResponse actualResponse = userService.updateStatus(data);

        assertEquals(expectedResponse, actualResponse);
        verify(userRepository).findByEmail(data.email());
        verify(userRepository).save(usuarioEncontrado);
        verify(userMapper).toDto(usuarioAtualizado);
    }

    @Test
    public void deveManterAtivoUsuario() {
        UserUpdateStatusRequest data = new UserUpdateStatusRequest("user@gmail.com", Boolean.TRUE);
        User usuarioEncontrado = UserFactory.criarUsuarioPersistidoCriadoPadrao();
        UserResponse expectedResponse = UserFactory.criarUserResponsePadrao();

        when(userRepository.findByEmail(data.email())).thenReturn(Optional.of(usuarioEncontrado));
        when(userMapper.toDto(usuarioEncontrado)).thenReturn(expectedResponse);

        UserResponse actualResponse = userService.updateStatus(data);

        assertEquals(expectedResponse, actualResponse);
        verify(userRepository).findByEmail(data.email());
        verify(userMapper).toDto(usuarioEncontrado);
    }

    @Test
    public void deveCriarUmNovoUsuarioComSucesso() {

        UserCreateRequest request = UserFactory.criarUserCreateRequestPadrao();
        User mappedEntity = UserFactory.criarUsuarioMapeadoPadrao();
        User usuarioConvertidoDeObject = UserFactory.criarUsuarioAdminPadrao();
        mappedEntity.setCriadoPor(usuarioConvertidoDeObject);
        User persistedUser = UserFactory.criarUsuarioPersistidoCriadoPadrao();
        UserResponse expectedResponse = UserFactory.criarUserResponsePadrao();

        when(userMapper.toEntityCreated(request)).thenReturn(mappedEntity);
        when(securityContextMock.getAuthentication()).thenReturn(authenticationMock);
        when(authenticationMock.getPrincipal()).thenReturn(usuarioConvertidoDeObject);
        when(bCryptPasswordEncoder.encode(request.senha())).thenReturn("$2a$12$m8tsnfw.x7mkVM1nHl1T8e5JtPxW52enFWtug43d2zU/mZxBPDRmC");
        when(userRepository.save(mappedEntity)).thenReturn(persistedUser);
        when(userMapper.toDto(persistedUser)).thenReturn(expectedResponse);

        SecurityContextHolder.setContext(securityContextMock);

        UserResponse actualResponse = userService.createUser(request);

        assertEquals(expectedResponse, actualResponse);

        verify(userMapper).toEntityCreated(request);
        verify(securityContextMock).getAuthentication();
        verify(authenticationMock).getPrincipal();
        verify(bCryptPasswordEncoder).encode(request.senha());
        verify(userRepository).save(mappedEntity);
        verify(userMapper).toDto(persistedUser);



    }
}
