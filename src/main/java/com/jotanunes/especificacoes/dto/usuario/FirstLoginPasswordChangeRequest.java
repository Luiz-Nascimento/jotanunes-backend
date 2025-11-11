package com.jotanunes.especificacoes.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class FirstLoginPasswordChangeRequest {

    @Email(message = "Email inválido")
    @NotBlank(message = "Email não pode ser vazio")
    private String email;

    @NotBlank(message = "A senha atual não pode ser vazia")
    private String senhaAtual;

    @NotBlank(message = "A nova senha não pode ser vazia")
    @Size(min = 6, message = "A nova senha deve ter pelo menos 6 caracteres")
    private String novaSenha;

    public FirstLoginPasswordChangeRequest() {}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenhaAtual() {
        return senhaAtual;
    }

    public void setSenhaAtual(String senhaAtual) {
        this.senhaAtual = senhaAtual;
    }

    public String getNovaSenha() {
        return novaSenha;
    }

    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
    }
}
