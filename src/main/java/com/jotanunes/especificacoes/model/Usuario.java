package com.jotanunes.especificacoes.model;


import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(nullable = false, unique = true, length = 80)
    private String nome;
    @Column(nullable = false)
    private String senha;
    @Column(name = "nivel_acesso", nullable = false, length = 10)
    private String nivelAcesso;
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

    public Usuario(UUID id, String nome, String senha, String nivelAcesso, String email, LocalDateTime dataCriacao, Usuario criadoPor, Boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.senha = senha;
        this.nivelAcesso = nivelAcesso;
        this.email = email;
        this.dataCriacao = dataCriacao;
        this.criadoPor = criadoPor;
        this.ativo = ativo;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public String getNivelAcesso() {
        return nivelAcesso;
    }

    public void setNivelAcesso(String nivelAcesso) {
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


}
