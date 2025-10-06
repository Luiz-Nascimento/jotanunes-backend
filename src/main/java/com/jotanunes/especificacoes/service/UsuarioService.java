package com.jotanunes.especificacoes.service;

import com.jotanunes.especificacoes.dto.UserResponse;
import com.jotanunes.especificacoes.mapper.UsuarioMapper;
import com.jotanunes.especificacoes.model.Usuario;
import com.jotanunes.especificacoes.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioMapper usuarioMapper;

    public List<UserResponse> findAll() {
        return usuarioRepository.findAll().stream().map(user -> usuarioMapper.toDto(user)).toList();
    }


}
