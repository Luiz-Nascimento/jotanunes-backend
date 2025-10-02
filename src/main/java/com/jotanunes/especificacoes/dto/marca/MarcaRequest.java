package com.jotanunes.especificacoes.dto.marca;

import jakarta.validation.constraints.NotBlank;

public class MarcaRequest {

    @NotBlank(message = "O nome da marca é obrigatório")
    private String nome;

    public MarcaRequest() {
    }

    public MarcaRequest(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}