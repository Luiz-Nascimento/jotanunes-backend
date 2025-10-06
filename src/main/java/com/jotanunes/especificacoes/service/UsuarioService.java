package com.jotanunes.especificacoes.service;

import com.jotanunes.especificacoes.dto.usuario.RoleChangeRequest;
import com.jotanunes.especificacoes.dto.usuario.UserResponse;
import com.jotanunes.especificacoes.exception.ResourceNotFoundException;
import com.jotanunes.especificacoes.mapper.UsuarioMapper;
import com.jotanunes.especificacoes.model.Usuario;
import com.jotanunes.especificacoes.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioMapper usuarioMapper;

    public List<UserResponse> findAll() {
        return usuarioRepository.findAll().stream().map(user -> usuarioMapper.toDto(user)).toList();
    }

    @Transactional
    public UserResponse updateUserRole(UUID id, RoleChangeRequest role) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow( () ->  new ResourceNotFoundException("Usuario nao encontrado com id: "+ id));
     usuario.setNivelAcesso(role.nivelAcesso());
     return usuarioMapper.toDto(usuario);

    }


}
