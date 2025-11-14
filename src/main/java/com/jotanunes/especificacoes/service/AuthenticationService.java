package com.jotanunes.especificacoes.service;

import com.jotanunes.especificacoes.dto.auth.LoginRequest;
import com.jotanunes.especificacoes.dto.auth.LoginResponse;
import com.jotanunes.especificacoes.infra.security.JwtUtil;
import com.jotanunes.especificacoes.model.User;
import com.jotanunes.especificacoes.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginResponse login(LoginRequest request) {
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.senha())
        );

        User user = (User) authentication.getPrincipal();

        if (!user.getAtivo()) {
            throw new RuntimeException("Usuário inativo. Contate o administrador.");
        }

        if (user.isAlterarSenha()) {
            throw new RuntimeException("É necessário alterar a senha antes de realizar o login.");
        }

        String token = jwtUtil.generateToken(user);
        return new LoginResponse(token, user.isAlterarSenha());

    }
}
