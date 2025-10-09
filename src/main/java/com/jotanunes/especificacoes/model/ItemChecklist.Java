package com.jotanunes.especificacoes.model;

import com.jotanunes.especificacoes.enums.StatusItem;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "itens_checklist")
public class ItemChecklist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String area;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusItem status;

    @Column(name = "motivo_reprovacao")
    private String motivoReprovacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empreendimento_id", nullable = false)
    private Empreendimento empreendimento;
    
    public ItemChecklist() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemChecklist that = (ItemChecklist) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}