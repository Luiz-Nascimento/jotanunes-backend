package com.jotanunes.especificacoes.service;

import com.jotanunes.especificacoes.repository.ItemRepository;
import com.jotanunes.especificacoes.repository.RevisaoItemRepository;
import org.springframework.stereotype.Service;

@Service
public class RevisaoItemService {

    private final RevisaoItemRepository repository;
    private final ItemRepository itemRepository;

    public RevisaoItemService(RevisaoItemRepository repository, ItemRepository itemRepository) {
        this.repository = repository;
        this.itemRepository = itemRepository;
    }

    //Revisar item
    //Caso o status seja aprovado, atualizar o status do item para APROVADO
    //Guardar informação do usuário que aprovou, salva a auditoria e retorna a revisão;
    //Caso o status seja reprovado, atualizar o status do item para reprovado,
    //Receber obrigatoriamente uma descrição do motivo da reprovação, guarda a auditoria e retorna a revisão.
    // Retornar erro caso o item esteja revisado(APROVADO ou REPROVAD0).
    // Retornar erro caso o item não exista.
    // Retornar erro caso o usuário não tenha permissão para revisar o item.

}
