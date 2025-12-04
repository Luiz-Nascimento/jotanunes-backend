package com.jotanunes.especificacoes.model;

import com.jotanunes.especificacoes.enums.TipoAmbiente;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

import java.util.Objects;

@Entity
@Table(name = "catalogo_ambientes")
public class CatalogoAmbiente {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome", nullable = false, length = 50)
    private String nome;

    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Column(columnDefinition= "ambiente_tipo", name = "tipo", nullable = false, length = 20)
    private TipoAmbiente tipo;

    public CatalogoAmbiente() {
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

    public TipoAmbiente getTipo() {
        return tipo;
    }

    public void setTipo(TipoAmbiente tipo) {
        this.tipo = tipo;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof CatalogoAmbiente that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(nome, that.nome) && tipo == that.tipo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, tipo);
    }
}
