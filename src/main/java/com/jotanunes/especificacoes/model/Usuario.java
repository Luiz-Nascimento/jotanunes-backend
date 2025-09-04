package com.jotanunes.especificacoes.model;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(nullable = false, length = 80)
    private String nome;
    @Column(nullable = false)
    private String senha;
    @Column(nullable = false, length = 12)
    private String perfil;
    @Column(nullable = false, unique = true, length = 255)
    private String email;
    @Column(name = "data_criacao", nullable = false)
    private Timestamp dataCriacao;
    @ManyToOne
    @JoinColumn(name = "criado_por")
    private Usuario criado_por;
    @Column(nullable = false)
    private Boolean ativo;

    public Usuario() {
    }

    public Usuario(UUID id, String nome, String senha, String perfil, String email, Timestamp dataCriacao, Usuario criado_por, Boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.senha = senha;
        this.perfil = perfil;
        this.email = email;
        this.dataCriacao = dataCriacao;
        this.criado_por = criado_por;
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

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Timestamp getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Timestamp dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Usuario getCriado_por() {
        return criado_por;
    }

    public void setCriado_por(Usuario criado_por) {
        this.criado_por = criado_por;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }


}
