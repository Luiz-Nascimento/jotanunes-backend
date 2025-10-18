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

    @ManyToOne
    @JoinColumn(name = "catalogo_item_id", nullable = false)
    private CatalogoItem catalogoItem;

    @Column(name = "descricao_customizada", length = 160)
    private String descricaoCustomizada;

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

    public ItemStatus getStatus() {
        return status;
    }

    public void setStatus(ItemStatus status) {
        this.status = status;
    }

    public String getDescricaoCustomizada() {
        return descricaoCustomizada;
    }

    public void setDescricaoCustomizada(String descricaoCustomizada) {
        this.descricaoCustomizada = descricaoCustomizada;
    }

    public CatalogoItem getCatalogoItem() {
        return catalogoItem;
    }

    public void setCatalogoItem(CatalogoItem catalogoItem) {
        this.catalogoItem = catalogoItem;
    }


    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Item item)) return false;
        return Objects.equals(id, item.id) && Objects.equals(ambiente, item.ambiente) && Objects.equals(catalogoItem, item.catalogoItem) && Objects.equals(descricaoCustomizada, item.descricaoCustomizada) && status == item.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ambiente, catalogoItem, descricaoCustomizada, status);
    }
}

