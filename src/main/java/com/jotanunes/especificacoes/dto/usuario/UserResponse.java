package com.jotanunes.especificacoes.dto.usuario;

public record UserResponse(String id, String nome, String email, String nivelAcesso, Boolean ativo) {
}
