package com.jotanunes.especificacoes.util;

import com.jotanunes.especificacoes.enums.EmpreendimentoStatus;
import com.jotanunes.especificacoes.model.Ambiente;
import com.jotanunes.especificacoes.model.Empreendimento;
import com.jotanunes.especificacoes.model.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class StatusVerifyCascadeUtil {

    private static final Logger logger = LoggerFactory.getLogger(StatusVerifyCascadeUtil.class);

    public void atualizarStatusCascade(Item item) {
        Ambiente ambiente = item.getAmbiente();
        // Verifica se todos os itens do ambiente estão aprovados
        boolean allApproved = ambiente.getItens().stream()
                .allMatch(i -> i.getStatus() == com.jotanunes.especificacoes.enums.ItemStatus.APROVADO);
        boolean anyPendente = ambiente.getItens().stream()
                .anyMatch(i -> i.getStatus() == com.jotanunes.especificacoes.enums.ItemStatus.PENDENTE);
        boolean anyReprovado = ambiente.getItens().stream()
                .anyMatch(i -> i.getStatus() == com.jotanunes.especificacoes.enums.ItemStatus.REPROVADO);
        // Caso todos itens estejam aprovados
        if (allApproved) {
            // Atualiza o status do ambiente para APROVADO
            ambiente.setStatus(com.jotanunes.especificacoes.enums.AmbienteStatus.APROVADO);
        }
        // Se não, caso algum item esteja pendente
        else if (anyPendente) {
            // Atualiza o status do ambiente para PENDENTE
            ambiente.setStatus(com.jotanunes.especificacoes.enums.AmbienteStatus.PENDENTE);
            logger.info("Ambiente ID {} status atualizado para PENDENTE", ambiente.getId());
        }
        // Se não, caso algum item esteja reprovado
        else if (anyReprovado) {
            // Atualiza o status do ambiente para REPROVADO
            ambiente.setStatus(com.jotanunes.especificacoes.enums.AmbienteStatus.REPROVADO);
            logger.info("Ambiente ID {} status atualizado para REPROVADO", ambiente.getId());
        }

    }

}