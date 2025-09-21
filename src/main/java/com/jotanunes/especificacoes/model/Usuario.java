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

    public Usuario(String nome, String senha, NivelAcesso nivelAcesso, String email, LocalDateTime dataCriacao, Usuario criadoPor, Boolean ativo) {
        this.nome = nome;
        this.senha = senha;
        this.nivelAcesso = nivelAcesso;
        this.email = email;
        this.dataCriacao = dataCriacao;
        this.criadoPor = criadoPor;
        this.ativo = ativo;
    }

    public Usuario(String nome, String senha, NivelAcesso nivelAcesso, String email, LocalDateTime dataCriacao, Boolean ativo) {
        this.nome = nome;
        this.senha = senha;
        this.nivelAcesso = nivelAcesso;
        this.email = email;
        this.dataCriacao = dataCriacao;
        this.ativo = ativo;
    }

    public UUID getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public NivelAcesso getNivelAcesso() {
        return nivelAcesso;
    }

    public void setNivelAcesso(NivelAcesso nivelAcesso) {
        this.nivelAcesso = nivelAcesso;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Usuario getCriadoPor() {
        return criadoPor;
    }

    public void setCriadoPor(Usuario criadoPor) {
        this.criadoPor = criadoPor;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
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
        return email;
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
