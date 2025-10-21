package com.jotanunes.especificacoes.model;

import com.jotanunes.especificacoes.enums.EmpreendimentoStatus;
import com.jotanunes.especificacoes.enums.LinhaEmpreendimento;
import com.jotanunes.especificacoes.enums.SistemaConstrutivo;
import com.jotanunes.especificacoes.enums.TipologiaEmpreendimento;
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
    @Column(columnDefinition = "tipologia_empreendimento", nullable = false)
    private TipologiaEmpreendimento tipologia;

    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Column(columnDefinition = "sistema_construtivo", nullable = false)
    private SistemaConstrutivo sistemaConstrutivo;

    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Column(columnDefinition = "linha_empreendimento", nullable = false)
    private LinhaEmpreendimento linha;

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
    private Set<CombinacaoEMM> materiaisPorMarca;

    public Empreendimento() {
    }

    public Empreendimento(TipologiaEmpreendimento tipologia, SistemaConstrutivo sistemaConstrutivo, LinhaEmpreendimento linha, String nome, String localizacao, String descricao, EmpreendimentoStatus status) {
        this.tipologia = tipologia;
        this.sistemaConstrutivo = sistemaConstrutivo;
        this.linha = linha;
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

    public TipologiaEmpreendimento getTipologia() {
        return tipologia;
    }

    public void setTipologia(TipologiaEmpreendimento tipologia) {
        this.tipologia = tipologia;
    }

    public SistemaConstrutivo getSistemaConstrutivo() {
        return sistemaConstrutivo;
    }

    public void setSistemaConstrutivo(SistemaConstrutivo sistemaConstrutivo) {
        this.sistemaConstrutivo = sistemaConstrutivo;
    }

    public LinhaEmpreendimento getLinha() {
        return linha;
    }

    public void setLinha(LinhaEmpreendimento linha) {
        this.linha = linha;
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
        this.ambientes = ambientes;
    }

    public Set<CombinacaoEMM> getMateriaisPorMarca() {
        return materiaisPorMarca;
    }

    public void setMateriaisPorMarca(Set<CombinacaoEMM> materiaisPorMarca) {
        this.materiaisPorMarca = materiaisPorMarca;
    }
}
