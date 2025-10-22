package com.jotanunes.especificacoes.service;

import com.jotanunes.especificacoes.dto.usuario.RoleChangeRequest;
import com.jotanunes.especificacoes.dto.usuario.UserCreateRequest;
import com.jotanunes.especificacoes.dto.usuario.UserResponse;
import com.jotanunes.especificacoes.dto.usuario.UserUpdateStatusRequest;
import com.jotanunes.especificacoes.exception.ResourceNotFoundException;
import com.jotanunes.especificacoes.mapper.UserMapper;
import com.jotanunes.especificacoes.model.User;
import com.jotanunes.especificacoes.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository userRepository, UserMapper userMapper, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserResponse> findAll() {
        return userRepository.findAll().stream().map(userMapper::toDto).toList();
    }

    @Transactional
    public UserResponse updateUserRole(UUID id, RoleChangeRequest role) {
        User user = userRepository.findById(id)
                .orElseThrow( () ->  new ResourceNotFoundException("Usuario nao encontrado com id: "+ id));
     user.setNivelAcesso(role.nivelAcesso());
     return userMapper.toDto(user);

    }

    public UserResponse createUser(UserCreateRequest request) {
        User userMapped = userMapper.toEntityCreated(request);
        userMapped.setSenha(passwordEncoder.encode(request.senha()));
        User userCreating = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userMapped.setCriadoPor(userCreating);
        User userPersisted = userRepository.save(userMapped);
        logger.info("Administrador {} registrou um novo usuario com email {} e acesso {}", userCreating.getEmail(), userPersisted.getEmail(), userPersisted.getNivelAcesso());
        return userMapper.toDto(userPersisted);
    }

    @Transactional
    public UserResponse updateStatus(UserUpdateStatusRequest data) {
        User persistedUser = userRepository.findByEmail(data.email())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário com esse email não existe."));
        if (Objects.equals(persistedUser.getAtivo(), data.novoStatus())) {
            return userMapper.toDto(persistedUser);
        }
        persistedUser.setAtivo(data.novoStatus());
        User updatedUser = userRepository.save(persistedUser);
        return userMapper.toDto(updatedUser);

    }


}
