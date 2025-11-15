package com.jotanunes.especificacoes.enums;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public enum NivelAcesso {

    PADRAO("ROLE_PADRAO", Set.of(
            Permissoes.CRIAR_EMPREENDIMENTOS,
            Permissoes.EDITAR_EMPREENDIMENTOS,
            Permissoes.DESATIVAR_EMPREENDIMENTOS
    )),

    GESTOR("ROLE_GESTOR", Set.of(
            Permissoes.REVISAR_EMPREENDIMENTOS,
            Permissoes.POPULAR_CATALOGOS
    )),

    ADMIN("ROLE_ADMIN", EnumSet.allOf(Permissoes.class));

    private final String role;
    private final Set<Permissoes> permissoes;

    NivelAcesso(String role, Set<Permissoes> permissoes) {
        this.role = role;
        this.permissoes = permissoes;
    }

    public String getRole() {
        return role;
    }

    public Set<Permissoes> getPermissoes() {
        return permissoes;
    }
}