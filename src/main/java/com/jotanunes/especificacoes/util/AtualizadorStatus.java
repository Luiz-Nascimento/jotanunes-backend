package com.jotanunes.especificacoes.util;

import com.jotanunes.especificacoes.enums.AmbienteStatus;
import com.jotanunes.especificacoes.enums.ItemStatus;
import com.jotanunes.especificacoes.model.Ambiente;
import com.jotanunes.especificacoes.model.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AtualizadorStatus {

    private static final Logger logger = LoggerFactory.getLogger(AtualizadorStatus.class);

    public void atualizarStatusAmbiente(Item item) {
        Ambiente ambiente = item.getAmbiente();
        Integer ambienteId = ambiente.getId();
        var itens = ambiente.getItens();
        //Regras de atualização:
        //Caso um ambiente contenha todos itens aprovados ele está aprovado.
        //Caso um ambiente contenha algum item pendente, ele está pendente.
        //Caso um ambiente contenha algum item reprovado e nenhum pendente, ele está reprovado.
        // Verificação de aprovação:
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
        //Verificação de pendência:
        if (algumItemPendente) {
            if (ambiente.getStatus() != AmbienteStatus.PENDENTE) {
                ambiente.setStatus(AmbienteStatus.PENDENTE);
                logger.info("Ambiente {} pendente", ambienteId);
            }
            return;
        }
        //Verificação de reprovação:
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