package com.jotanunes.especificacoes.controller;

import com.jotanunes.especificacoes.dto.auth.LoginRequest;
import com.jotanunes.especificacoes.dto.auth.LoginResponse;
import com.jotanunes.especificacoes.dto.auth.PasswordChangeRequiredResponse;
import com.jotanunes.especificacoes.dto.usuario.FirstLoginPasswordChangeRequest;
import com.jotanunes.especificacoes.exception.ResourceNotFoundException;
import com.jotanunes.especificacoes.repository.UserRepository;
import com.jotanunes.especificacoes.service.AuthorizationService;
import com.jotanunes.especificacoes.infra.security.JwtUtil;
import com.jotanunes.especificacoes.service.UserService;
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

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Operation(
            summary = "Login de usuário",
            description = "Autentica o usuário e retorna um token JWT"
    )
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.senha())
        );

        UserDetails userDetails = authorizationService.loadUserByUsername(request.email());

        if (userDetails instanceof com.jotanunes.especificacoes.model.User user) {
            if (user.isAlterarSenha()) {
                PasswordChangeRequiredResponse body = new PasswordChangeRequiredResponse(
                        "É necessário alterar a senha antes de realizar o login.",
                        true
                );
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(body);
            }
        }
        String token = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new LoginResponse(token));
    }

    @Operation(
            summary = "Troca de senha - primeiro acesso",
            description = "Endpoint utilizado quando o usuário está com alterarSenha = true. Recebe email, senha atual e nova senha."
    )
    @PostMapping("/primeiro-acesso/trocar-senha")
    public ResponseEntity<?> trocarSenhaPrimeiroAcesso(@RequestBody @Valid FirstLoginPasswordChangeRequest request) {
        try {
            userService.changePasswordFirstLogin(request);
            return ResponseEntity.ok("Senha alterada com sucesso. Agora faça login normalmente.");
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(404).body(ex.getMessage());
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (IllegalStateException ex) {
            return ResponseEntity.status(409).body(ex.getMessage());
        }
    }
}
