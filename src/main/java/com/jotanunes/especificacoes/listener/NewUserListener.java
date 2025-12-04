package com.jotanunes.especificacoes.listener;


import com.jotanunes.especificacoes.event.NewUserEvent;
import com.jotanunes.especificacoes.service.EmailService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.Year;
import java.util.HashMap;
import java.util.Map;

@Component
public class NewUserListener {

    private final EmailService emailService;

    public NewUserListener(EmailService emailService) {
        this.emailService = emailService;
    }


    @EventListener
    public void handleNewUserCreated(NewUserEvent event) {
        Map<String, Object> variaveis = new HashMap<>();
        variaveis.put("criadoPor", event.criadoPor());
        variaveis.put("email", event.email());
        variaveis.put("senha", event.senha());
        variaveis.put("ano", Year.now().getValue());
        String assunto = "Conta cadastrada";

        emailService.notificarNovoUsuario(event.email(), assunto, variaveis);
    }
}
