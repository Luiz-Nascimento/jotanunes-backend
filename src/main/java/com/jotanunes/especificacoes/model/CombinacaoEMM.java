package com.jotanunes.especificacoes.model;


import jakarta.persistence.*;

@Entity
@Table(name = "empreendimento_material_marca")
public class CombinacaoEMM {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "empreendimento_id")
    private Empreendimento empreendimento;

    @ManyToOne
    @JoinColumn(name = "material_id")
    private Material material;

    @ManyToOne
    @JoinColumn(name = "marca_id")
    private Marca marca;

    public CombinacaoEMM() {
    }

    public CombinacaoEMM(Empreendimento empreendimento, Material material, Marca marca) {
        this.empreendimento = empreendimento;
        this.material = material;
        this.marca = marca;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Empreendimento getEmpreendimento() {
        return empreendimento;
    }

    public void setEmpreendimento(Empreendimento empreendimento) {
        this.empreendimento = empreendimento;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }
}
