package com.jotanunes.especificacoes.model;


import com.jotanunes.especificacoes.enums.AmbienteStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "ambientes")
public class Ambiente {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "empreendimento_id")
    private Empreendimento empreendimento;

    @ManyToOne
    @JoinColumn(name = "catalogo_ambiente_id", nullable = false)
    private CatalogoAmbiente catalogoAmbiente;

    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Column(columnDefinition = "ambiente_status", nullable = false)
    private AmbienteStatus status = AmbienteStatus.PENDENTE;

    @OneToMany(mappedBy = "ambiente", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Item> itens = new HashSet<>();

    public Empreendimento getEmpreendimento() {
        return empreendimento;
    }

    public void setEmpreendimento(Empreendimento empreendimento) {
        this.empreendimento = empreendimento;
    }

    public Ambiente() {
    }

    public CatalogoAmbiente getCatalogoAmbiente() {
        return catalogoAmbiente;
    }

    public void setCatalogoAmbiente(CatalogoAmbiente catalogoAmbiente) {
        this.catalogoAmbiente = catalogoAmbiente;
    }

    public AmbienteStatus getStatus() {
        return status;
    }

    public void setStatus(AmbienteStatus status) {
        this.status = status;
    }


    public Integer getId() {
        return id;
    }

    public Set<Item> getItens() {
        return itens;
    }

    public void setItens(Set<Item> itens) {
        this.itens = itens;
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
                '}';
    }
}
