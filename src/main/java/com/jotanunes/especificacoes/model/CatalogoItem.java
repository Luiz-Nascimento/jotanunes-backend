package com.jotanunes.especificacoes.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "catalogo_itens")
public class CatalogoItem {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "ambiente_id", nullable = false)
    private CatalogoAmbiente ambiente;

    public CatalogoItem() {
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public CatalogoAmbiente getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(CatalogoAmbiente ambiente) {
        this.ambiente = ambiente;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof CatalogoItem that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(nome, that.nome) && Objects.equals(descricao, that.descricao) && Objects.equals(ambiente, that.ambiente);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, descricao, ambiente);
    }
}
