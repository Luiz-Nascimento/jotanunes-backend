package com.jotanunes.especificacoes.controller;

import com.jotanunes.especificacoes.dto.UsuarioExibirDTO;
import com.jotanunes.especificacoes.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UserController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public List<UsuarioExibirDTO> listarTodos() {
        return usuarioService.listarTodos();
    }
}
