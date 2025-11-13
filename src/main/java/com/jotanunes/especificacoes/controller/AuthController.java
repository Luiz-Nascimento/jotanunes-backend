package com.jotanunes.especificacoes.controller;

import com.jotanunes.especificacoes.dto.auth.LoginRequest;
import com.jotanunes.especificacoes.dto.auth.LoginResponse;
import com.jotanunes.especificacoes.dto.usuario.FirstLoginPasswordChangeRequest;
import com.jotanunes.especificacoes.service.AuthenticationService;
import com.jotanunes.especificacoes.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Autenticação", description = "Operações relacionadas a autenticação de usuários")
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationService authenticationService;
    private final UserService userService;

    public AuthController(AuthenticationService authenticationService, UserService userService) {
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    @Operation(
            summary = "Login de usuário",
            description = "Autentica o usuário e retorna um token JWT. Se o usuário precisar alterar a senha, o login será bloqueado até a troca."
    )
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest request) {
        return ResponseEntity.ok(authenticationService.login(request));
    }

    @Operation(
            summary = "Troca de senha no primeiro login",
            description = "Permite ao usuário alterar a senha inicial antes de conseguir logar no sistema."
    )
    @PostMapping("/first-login-password-change")
    public ResponseEntity<String> firstLoginPasswordChange(@RequestBody @Valid FirstLoginPasswordChangeRequest request) {
        userService.changePasswordOnFirstLogin(request);
        return ResponseEntity.ok("Senha alterada com sucesso. Agora você já pode fazer login.");
    }
}
