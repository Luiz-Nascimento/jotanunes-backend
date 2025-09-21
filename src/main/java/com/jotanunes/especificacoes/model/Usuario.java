package com.jotanunes.especificacoes.model;


import com.jotanunes.especificacoes.enums.NivelAcesso;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "usuarios")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true, length = 30)
    private String nome;
    @Column(nullable = false)
    private String senha;
    @Column(name = "nivel_acesso", nullable = false, length = 10)
    private NivelAcesso nivelAcesso;
    @Column(nullable = false, unique = true, length = 255)
    private String email;
    @Column(name = "data_criacao", nullable = false)
    @CreationTimestamp
    private LocalDateTime dataCriacao;
    @ManyToOne
    @JoinColumn(name = "criado_por")
    private Usuario criadoPor;
    @Column(nullable = false)
    private Boolean ativo;

    public Usuario() {
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.nivelAcesso == NivelAcesso.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN")
                , new SimpleGrantedAuthority("ROLE_GESTOR"),
                new SimpleGrantedAuthority("ROLE_PADRAO"));
        else if (this.nivelAcesso == NivelAcesso.GESTOR) return List.of(new SimpleGrantedAuthority("ROLE_GESTOR")
        , new SimpleGrantedAuthority("ROLE_PADRAO"));

        return List.of(new SimpleGrantedAuthority("ROLE_PADRAO"));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return nome;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
