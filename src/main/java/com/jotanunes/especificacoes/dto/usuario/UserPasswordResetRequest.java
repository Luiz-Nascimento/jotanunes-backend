package com.jotanunes.especificacoes.dto.usuario;

public class UserPasswordResetRequest {

    private String novaSenha;

    public String getNovaSenha()
    {
        return novaSenha;
    }

    public void setNovaSenha(String novaSenha)
    {
        this.novaSenha = novaSenha;
    }
}