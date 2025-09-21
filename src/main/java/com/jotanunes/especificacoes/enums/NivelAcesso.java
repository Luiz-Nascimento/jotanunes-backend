package com.jotanunes.especificacoes.enums;

public enum NivelAcesso {
    PADRAO("ROLE_PADRAO"),
    GESTOR("ROLE_GESTOR"),
    ADMIN("ROLE_ADMIN");

    private String acesso;

    NivelAcesso(String acesso) {
        this.acesso = acesso;
    }

    public String getAcesso() {
        return acesso;
    }

}
