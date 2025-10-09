package com.jotanunes.especificacoes.controller;

import com.jotanunes.especificacoes.dto.usuario.RoleChangeRequest;
import com.jotanunes.especificacoes.dto.usuario.UserResponse;
import com.jotanunes.especificacoes.service.UsuarioService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Usuários", description = "Operações relacionadas ao manipulamento de usuários")
@RestController
@RequestMapping("/usuarios")
public class UserController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public List<UserResponse> findAll() {
        return usuarioService.findAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/role")
    public ResponseEntity<UserResponse> updateUserRole(@PathVariable UUID id, @RequestBody RoleChangeRequest role) {
        UserResponse response = usuarioService.updateUserRole(id, role);
        return ResponseEntity.ok(response);
    }
}
