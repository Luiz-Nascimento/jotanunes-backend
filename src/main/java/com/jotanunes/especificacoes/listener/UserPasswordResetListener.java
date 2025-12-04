package com.jotanunes.especificacoes.listener;


import com.jotanunes.especificacoes.event.UserPasswordResetEvent;
import com.jotanunes.especificacoes.service.EmailService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserPasswordResetListener {

    private final EmailService emailService;

    public UserPasswordResetListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @EventListener
    public void handleUserPasswordReset(UserPasswordResetEvent event) {
        Map<String, Object> variaveis = new HashMap<>();
        variaveis.put("username", event.usuario());
        variaveis.put("senhaTemporaria", event.senhaTemporaria());
        String assunto = "Senha redefinida";
        String destinatario = event.email();
        emailService.notificarResetSenha(destinatario, assunto, variaveis);
    }
}
