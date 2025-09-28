package com.jotanunes.especificacoes.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "empreendimentos")
public class Empreendimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String localizacao;

    @OneToMany(mappedBy = "empreendimento", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Ambiente> ambientes = new HashSet<>();

    @Column(nullable = false)
    private String descricao;

    private String observacoes;

    public Empreendimento() {
    }

    public Empreendimento(String nome, String localizacao, String descricao) {
        this.nome = nome;
        this.localizacao = localizacao;
        this.descricao = descricao;
    }

    public Empreendimento(String nome, String localizacao, String descricao, String observacoes) {
        this.nome = nome;
        this.localizacao = localizacao;
        this.descricao = descricao;
        this.observacoes = observacoes;
    }

    public Empreendimento(Integer id, String nome, String localizacao, Set<Ambiente> ambientes, String descricao, String observacoes) {
        this.id = id;
        this.nome = nome;
        this.localizacao = localizacao;
        this.ambientes = ambientes;
        this.descricao = descricao;
        this.observacoes = observacoes;
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Set<Ambiente> getAmbientes() {
        return ambientes;
    }

    public void setAmbientes(Set<Ambiente> ambientes) {
        this.ambientes = ambientes;
    }

    public void printAmbientes() {
        this.ambientes.forEach(System.out::println);
    }
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Empreendimento that = (Empreendimento) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
