package com.jotanunes.especificacoes.service;

import com.jotanunes.especificacoes.dto.UsuarioExibirDTO;
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

    public List<UsuarioExibirDTO> listarTodos() {
        List<UsuarioExibirDTO> usuarios = new ArrayList<>();
        for (Usuario usuario: usuarioRepository.findAll()) {
            usuarios.add(usuarioMapper.toDto(usuario));
            System.out.println(usuarioMapper.toDto(usuario));
        }
        for (UsuarioExibirDTO usuarioExibirDTO: usuarios) {
            System.out.println(usuarioExibirDTO);
        }
        return usuarios;
    }

}
