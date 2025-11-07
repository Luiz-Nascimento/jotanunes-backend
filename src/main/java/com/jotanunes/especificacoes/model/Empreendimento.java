package com.jotanunes.especificacoes.model;

import com.jotanunes.especificacoes.enums.*;
import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcType;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;
import org.hibernate.type.SqlTypes;

import java.util.*;

@Entity
@Table(name = "empreendimentos")
public class Empreendimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Column(columnDefinition = "segmento_empreendimento", nullable = false)
    private SegmentoEmpreendimento segmento;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String localizacao;

    @Column(nullable = false)
    private String descricao;

    @Type(ListArrayType.class)
    @Column(
            name = "observacoes",
            columnDefinition = "text[]"
    )
    private List<String> observacoes = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Column(columnDefinition = "empreendimento_status", nullable = false)
    private EmpreendimentoStatus status = EmpreendimentoStatus.PENDENTE;

    @OneToMany(mappedBy = "empreendimento", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Ambiente> ambientes = new HashSet<>();

    @OneToMany(mappedBy = "empreendimento")
    private Set<CombinacaoEMM> materiaisPorMarca = new HashSet<>();

    public Empreendimento() {
    }

    public Empreendimento(SegmentoEmpreendimento segmento, String nome, String localizacao, String descricao, EmpreendimentoStatus status) {
        this.segmento = segmento;
        this.nome = nome;
        this.localizacao = localizacao;
        this.descricao = descricao;
        this.status = status;
    }

    public Empreendimento(Integer id, SegmentoEmpreendimento segmento, String nome, String localizacao, String descricao, EmpreendimentoStatus status) {
        this.id = id;
        this.segmento = segmento;
        this.nome = nome;
        this.localizacao = localizacao;
        this.descricao = descricao;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public SegmentoEmpreendimento getSegmento() {
        return segmento;
    }

    public void setSegmento(SegmentoEmpreendimento segmento) {
        this.segmento = segmento;
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

    public List<String> getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(List<String> observacoes) {
        this.observacoes = observacoes;
    }

    public EmpreendimentoStatus getStatus() {
        return status;
    }

    public void setStatus(EmpreendimentoStatus status) {
        this.status = status;
    }

    public Set<Ambiente> getAmbientes() {
        return ambientes;
    }

    public void setAmbientes(Set<Ambiente> ambientes) {
        if(!this.ambientes.isEmpty()) this.ambientes.clear();
        this.ambientes.addAll(ambientes);
    }

    public Set<CombinacaoEMM> getMateriaisPorMarca() {
        return materiaisPorMarca;
    }

    public void setMateriaisPorMarca(Set<CombinacaoEMM> materiaisPorMarca) {

        if(!this.materiaisPorMarca.isEmpty()) this.materiaisPorMarca.clear();
        this.materiaisPorMarca.addAll(materiaisPorMarca);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Empreendimento)) return false;
        Empreendimento that = (Empreendimento) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
