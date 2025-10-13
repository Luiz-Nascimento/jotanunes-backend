package com.jotanunes.especificacoes.model;

import com.jotanunes.especificacoes.enums.ItemStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "revisao_item")
public class RevisaoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "item_id", referencedColumnName = "id", nullable = false, unique = true)
    private Item item;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ItemStatus status;

    @Column(name = "motivo")
    private String motivo;

    @ManyToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id", nullable = false)
    private Usuario usuario;

    @UpdateTimestamp
    @Column(name = "data_avaliacao", nullable = false)
    private LocalDateTime dataAvaliacao;

    public RevisaoItem() {
    }
    public RevisaoItem(Item item, ItemStatus status, String motivo, Usuario usuario) {
        this.item = item;
        this.status = status;
        this.motivo = motivo;
        this.usuario = usuario;
    }
    public Integer getId() {
        return id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public ItemStatus getStatus() {
        return status;
    }

    public void setStatus(ItemStatus status) {
        this.status = status;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public LocalDateTime getDataAvaliacao() {
        return dataAvaliacao;
    }

    public void setDataAvaliacao(LocalDateTime dataAvaliacao) {
        this.dataAvaliacao = dataAvaliacao;
    }
}