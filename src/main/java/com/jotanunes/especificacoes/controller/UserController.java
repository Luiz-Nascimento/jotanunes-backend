package com.jotanunes.especificacoes.controller;

import com.jotanunes.especificacoes.dto.usuario.*;
import com.jotanunes.especificacoes.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    private UserService userService;

    @Operation(
            summary = "Listar todos os usuários",
            description = "Retorna uma lista de todos os usuários cadastrados no sistema. Apenas administradores podem acessar essa informação."
    )
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<UserResponse> findAll() {
        return userService.findAll();
    }

    @Operation(
            summary = "Alterar o papel de um usuário",
            description = "Permite alterar o papel (role) de um usuário específico. Apenas administradores podem realizar essa ação."
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/role")
    public ResponseEntity<UserResponse> updateUserRole(@PathVariable UUID id, @RequestBody RoleChangeRequest role) {
        UserResponse response = userService.updateUserRole(id, role);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Criar um usuário",
            description = "Permite um ADMIN cadastrar um novo usuário no sistema"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserCreateRequest request) {
        UserResponse response = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Atualizar status de um usuário",
            description = "Permite um ADMIN atualizar o status de um usuário de email e novo status especificado."
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/atualizar-status")
    public ResponseEntity<UserResponse> updateStatusUser(@RequestBody @Valid UserUpdateStatusRequest request) {
        UserResponse response = userService.updateStatus(request);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{id}/definir-senha")
    public ResponseEntity<Void> adminSetPassword(@PathVariable UUID id, @RequestBody UserPasswordResetRequest request) {
        userService.adminSetPassword(id, request);
        return ResponseEntity.noContent().build();

    }

}
