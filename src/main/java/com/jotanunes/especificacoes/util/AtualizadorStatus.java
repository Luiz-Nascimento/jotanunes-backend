package com.jotanunes.especificacoes.util;

import com.jotanunes.especificacoes.enums.AmbienteStatus;
import com.jotanunes.especificacoes.enums.ItemStatus;
import com.jotanunes.especificacoes.model.Ambiente;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AtualizadorStatus {

    private static final Logger logger = LoggerFactory.getLogger(AtualizadorStatus.class);

    public void ambienteStatusUpdate(Ambiente ambiente) {
        Integer ambienteId = ambiente.getId();
        var itens = ambiente.getItens();
        boolean todosItensAprovados = itens.stream().allMatch
                (i -> i.getStatus() == ItemStatus.APROVADO);
        if (todosItensAprovados) {
            if (ambiente.getStatus() != AmbienteStatus.APROVADO) {
                ambiente.setStatus(AmbienteStatus.APROVADO);
                logger.info("Ambiente {} aprovado", ambienteId);
            }
            return;
        }
        boolean algumItemPendente = itens.stream().anyMatch(
                i -> i.getStatus() == ItemStatus.PENDENTE);
        if (algumItemPendente) {
            if (ambiente.getStatus() != AmbienteStatus.PENDENTE) {
                ambiente.setStatus(AmbienteStatus.PENDENTE);
                logger.info("Ambiente {} pendente", ambienteId);
            }
            return;
        }
        boolean algumItemReprovado = itens.stream().anyMatch(
                i -> i.getStatus() == ItemStatus.REPROVADO);
        if (algumItemReprovado) {
            if (ambiente.getStatus() != AmbienteStatus.REPROVADO) {
                ambiente.setStatus(AmbienteStatus.REPROVADO);
                logger.info("Ambiente {} reprovado", ambienteId);
            }
        }
    }



}