package com.jotanunes.especificacoes.service;

import com.jotanunes.especificacoes.dto.auth.RegisterRequest;
import com.jotanunes.especificacoes.exception.UserAlreadyRegisteredException;
import com.jotanunes.especificacoes.mapper.UsuarioMapper;
import com.jotanunes.especificacoes.model.Usuario;
import com.jotanunes.especificacoes.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService  implements UserDetailsService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    UsuarioMapper usuarioMapper;

    Logger logger = LoggerFactory.getLogger(AuthorizationService.class);

    @Override
    public UserDetails loadUserByUsername(String username) {
        return usuarioRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }

    public void register(RegisterRequest data) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (usuarioRepository.existsByEmail(data.email()) || usuarioRepository.existsByNome(data.nome())) {
            throw new UserAlreadyRegisteredException("Usuario ja registrado com esse email ou nome");
        }
        Usuario user = usuarioMapper.toEntity(data);
        user.setSenha(passwordEncoder.encode(user.getSenha()));
        Usuario userSalvo = usuarioRepository.save(user);
        logger.info("Usuário registrado com UUID: {} ", userSalvo.getId());
    }


}
