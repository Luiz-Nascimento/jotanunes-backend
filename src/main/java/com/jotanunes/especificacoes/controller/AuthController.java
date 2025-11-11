package com.jotanunes.especificacoes.controller;

import com.jotanunes.especificacoes.dto.auth.LoginRequest;
import com.jotanunes.especificacoes.dto.auth.LoginResponse;
import com.jotanunes.especificacoes.service.AuthenticationService;
import com.jotanunes.especificacoes.service.AuthorizationService;
import com.jotanunes.especificacoes.infra.security.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Autenticação", description = "Operações relacionadas a autenticação de usuários")
@RestController
@RequestMapping("/auth")
public class AuthController {

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    private final AuthenticationService authenticationService;

    @Operation(
            summary = "Login de usuário",
            description = "Autentica o usuário e retorna um token JWT"
    )
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        LoginResponse response = authenticationService.login(request);
        return ResponseEntity.ok(response);
    }

}
