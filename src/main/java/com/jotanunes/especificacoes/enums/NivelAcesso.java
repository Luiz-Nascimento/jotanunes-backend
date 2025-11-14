package com.jotanunes.especificacoes.enums;

import java.util.List;

public enum NivelAcesso {

    PADRAO("ROLE_PADRAO", List.of(
            "EMPREENDIMENTO_CRIAR",
            "EMPREENDIMENTO_EDITAR",
            "EMPREENDIMENTO_DELETAR"
    )),

    GESTOR("ROLE_GESTOR", List.of(
            "EMPREENDIMENTO_REVISAR"
    )),

    ADMIN("ROLE_ADMIN", List.of(
            "USUARIO_CRIAR",
            "USUARIO_EDITAR",
            "USUARIO_DELETAR",
            "USUARIO_LISTAR",
            "EMPREENDIMENTO_CRIAR",
            "EMPREENDIMENTO_EDITAR",
            "EMPREENDIMENTO_DELETAR",
            "EMPREENDIMENTO_REVISAR"
    ));

    private final String role;
    private final List<String> permissoes;

    NivelAcesso(String role, List<String> permissoes) {
        this.role = role;
        this.permissoes = permissoes;
    }

    public String getRole() {
        return role;
    }

    public List<String> getPermissoes() {
        return permissoes;
    }
}