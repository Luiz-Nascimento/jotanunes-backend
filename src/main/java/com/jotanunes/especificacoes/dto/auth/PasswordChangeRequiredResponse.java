package com.jotanunes.especificacoes.dto.auth;

public class PasswordChangeRequiredResponse {
    private String message;
    private boolean needsPasswordChange;

    public PasswordChangeRequiredResponse(String message, boolean needsPasswordChange) {
        this.message = message;
        this.needsPasswordChange = needsPasswordChange;
    }

    public String getMessage() {
        return message;
    }

    public boolean isNeedsPasswordChange() {
        return needsPasswordChange;
    }
}