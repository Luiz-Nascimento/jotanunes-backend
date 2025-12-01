package com.jotanunes.especificacoes.listener;

import com.jotanunes.especificacoes.event.AmbienteAtualizadoEvent;
import com.jotanunes.especificacoes.util.AtualizadorStatus;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AmbienteEventListener {

    private final AtualizadorStatus atualizadorStatus;

    public AmbienteEventListener(AtualizadorStatus atualizadorStatus) {
        this.atualizadorStatus = atualizadorStatus;
    }

    @EventListener
    public void handleItemAtualizado(AmbienteAtualizadoEvent event) {
        atualizadorStatus.ambienteStatusUpdate(event.ambiente());
    }
}
