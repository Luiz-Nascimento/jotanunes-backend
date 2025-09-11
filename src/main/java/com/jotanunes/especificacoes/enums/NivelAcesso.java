package com.jotanunes.especificacoes.enums;

public enum NivelAcesso {
    PADRAO("PADRAO", "Usuário Padrão"),
    GESTOR("GESTOR", "Gestor"),
    ADMIN("ADMIN", "Administrador");

    private final String codigo;
    private final String descricao;

    NivelAcesso(String codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static NivelAcesso fromCodigo(String codigo) {
        for (NivelAcesso nivel : NivelAcesso.values()) {
            if (nivel.codigo.equals(codigo)) {
                return nivel;
            }
        }
        throw new IllegalArgumentException("Nível de acesso inválido: " + codigo);
    }

}
