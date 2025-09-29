package com.jotanunes.especificacoes.model;


import com.jotanunes.especificacoes.enums.ItemStatus;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "itens")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ambiente_id")
    private Ambiente ambiente;

    @Column(nullable = false, length = 40)
    private String nome;

    @Column(nullable = false)
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ItemStatus status = ItemStatus.PENDENTE;

    public Item() {
    }

    public Integer getId() {
        return id;
    }


    public Ambiente getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(Ambiente ambiente) {
        this.ambiente = ambiente;
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

    public ItemStatus getStatus() {
        return status;
    }

    public void setStatus(ItemStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Item item)) return false;
        return Objects.equals(id, item.id) && Objects.equals(ambiente, item.ambiente) && Objects.equals(nome, item.nome) && Objects.equals(descricao, item.descricao) && status == item.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ambiente, nome, descricao, status);
    }
}
