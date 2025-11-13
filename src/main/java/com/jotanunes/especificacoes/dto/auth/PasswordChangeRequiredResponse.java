package com.jotanunes.especificacoes.dto.auth;

public class PasswordChangeRequiredResponse {
    private String message;
    private boolean passwordChangeRequired;

    public PasswordChangeRequiredResponse(String message, boolean passwordChangeRequired) {
        this.message = message;
        this.passwordChangeRequired = passwordChangeRequired;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isPasswordChangeRequired() {
        return passwordChangeRequired;
    }

    public void setPasswordChangeRequired(boolean passwordChangeRequired) {
        this.passwordChangeRequired = passwordChangeRequired;
    }
}
