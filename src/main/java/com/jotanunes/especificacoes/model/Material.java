package com.jotanunes.especificacoes.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "materiais")
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 100)
    private String nome;

    @OneToMany(mappedBy = "material")
    private Set<CombinacaoEMM> empreendimentosComMarca;


    public Material() {
    }

    public Material(String nome) {
        this.nome = nome;
    }

    public Material(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}