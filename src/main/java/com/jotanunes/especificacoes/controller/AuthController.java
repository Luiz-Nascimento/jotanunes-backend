package com.jotanunes.especificacoes.controller;

import com.jotanunes.especificacoes.dto.auth.LoginRequest;
import com.jotanunes.especificacoes.dto.auth.LoginResponse;
import com.jotanunes.especificacoes.dto.auth.RegisterRequest;
import com.jotanunes.especificacoes.model.Usuario;
import com.jotanunes.especificacoes.enums.NivelAcesso;
import com.jotanunes.especificacoes.repository.UsuarioRepository;
import com.jotanunes.especificacoes.service.AuthorizationService;
import com.jotanunes.especificacoes.infra.security.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Autenticação", description = "Operações relacionadas a autenticação de usuários")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private JwtUtil jwtUtil;

    @Operation(
            summary = "Login de usuário",
            description = "Autentica o usuário e retorna um token JWT"
    )
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.senha())
        );
        UserDetails userDetails = authorizationService.loadUserByUsername(request.email());
        String token = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new LoginResponse(token));
    }

    @Operation(
            summary = "Registro de novo usuário",
            description = "Registra um novo usuário com nível de acesso padrão"
    )
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        authorizationService.register(request);
        return ResponseEntity.ok().build();
    }
}
