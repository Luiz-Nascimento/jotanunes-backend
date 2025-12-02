package com.jotanunes.especificacoes.listener;

import com.jotanunes.especificacoes.enums.NivelAcesso;
import com.jotanunes.especificacoes.event.EmpreendimentoPendenteEvent;
import com.jotanunes.especificacoes.model.User;
import com.jotanunes.especificacoes.repository.UserRepository;
import com.jotanunes.especificacoes.service.EmailService;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        List<User> gestores = userRepository.findByNivelAcessoAndAtivoTrue(NivelAcesso.GESTOR);
        String assunto = "Empreendimento pendente";
        for (User gestor: gestores) {
            Map<String, Object> vars = new HashMap<>();
            vars.put("empreendimento", event.nomeEmpreendimento());
            vars.put("enviadoPor", event.enviadoPor());
            vars.put("nomeGestor", gestor.getNome());
            emailService.notificarEmpreendimentoPendente(gestor.getEmail(), assunto, vars);
        }
    }
}
