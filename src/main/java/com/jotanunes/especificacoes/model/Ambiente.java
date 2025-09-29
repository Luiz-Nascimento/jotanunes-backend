package com.jotanunes.especificacoes.model;


import com.jotanunes.especificacoes.enums.TipoAmbiente;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "ambientes")
public class Ambiente {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "empreendimento_id")
    private Empreendimento empreendimento;

    @Column(length = 80, nullable = false)
    private String nome;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoAmbiente tipo;

    public Empreendimento getEmpreendimento() {
        return empreendimento;
    }

    public void setEmpreendimento(Empreendimento empreendimento) {
        this.empreendimento = empreendimento;
    }

    public Ambiente() {
    }

    public Ambiente(Empreendimento empreendimento, String nome, TipoAmbiente tipo) {
        this.empreendimento = empreendimento;
        this.nome = nome;
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TipoAmbiente getTipo() {
        return tipo;
    }

    public void setTipo(TipoAmbiente tipo) {
        this.tipo = tipo;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Ambiente ambiente = (Ambiente) o;
        return Objects.equals(id, ambiente.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Ambiente{" +
                "id=" + id +
                ", empreendimento=" + empreendimento.getNome() +
                ", nome='" + nome + '\'' +
                ", tipo=" + tipo +
                '}';
    }
}
