package com.jotanunes.especificacoes.listener;

import com.jotanunes.especificacoes.event.EmpreendimentoPendenteEvent;
import com.jotanunes.especificacoes.repository.UserRepository;
import com.jotanunes.especificacoes.service.EmailService;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NotificarGestoresListener {

    private final UserRepository userRepository;
    private final EmailService emailService;

    public NotificarGestoresListener(UserRepository userRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    @Async
    @EventListener
    public void handleEmpreendimentoPendente(EmpreendimentoPendenteEvent event) {
        List<String> emailGestoresAtivos = userRepository.findEmailGestoresAtivos();
        String assunto = "Novo empreendimento pendente";
        String messagem = "Olá gestor, o empreendimento " + event.nomeEmpreendimento()
                + " foi criado no sistema e enviado para revisão!";
        emailService.enviarEmailTexto(emailGestoresAtivos, assunto, messagem);
    }
}
