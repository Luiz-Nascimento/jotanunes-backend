package com.jotanunes.especificacoes.service;

import com.jotanunes.especificacoes.dto.item.ItemDocResponse;
import com.jotanunes.especificacoes.dto.item.ItemRequest;
import com.jotanunes.especificacoes.dto.item.ItemResponse;
import com.jotanunes.especificacoes.exception.ResourceNotFoundException;
import com.jotanunes.especificacoes.mapper.ItemMapper;
import com.jotanunes.especificacoes.model.Ambiente;
import com.jotanunes.especificacoes.model.Item;
import com.jotanunes.especificacoes.repository.AmbienteRepository;
import com.jotanunes.especificacoes.repository.ItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ItemService {

    private final ItemRepository repository;
    private final ItemMapper mapper;
    private final AmbienteRepository ambienteRepository;

    private final Logger logger = LoggerFactory.getLogger(ItemService.class);

    public ItemService(ItemRepository repository, ItemMapper mapper, AmbienteRepository ambienteRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.ambienteRepository = ambienteRepository;
    }

    public List<ItemResponse> getAllItens() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    public ItemResponse getItemById(Integer id) {
        return mapper.toDto(repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item nao encontrado com id: " + id)));
    }

    public ItemDocResponse getItemDocResponse(Integer id) {
        return mapper.toDocResponse(repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item não encontrado com id: " + id)));
    }

    @Transactional
    public ItemResponse createItem(ItemRequest data, Integer id) {
        Ambiente ambiente = ambienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ambiente nao encontrado com id: " + id));
        Item item = mapper.toEntity(data);
        item.setAmbiente(ambiente);
        Item itemSalvo = repository.save(item);
        ambiente.getItens().add(item);
        logger.info("Novo item criado com id {}, associado ao ambiente: {}", itemSalvo.getId(), id);
        return mapper.toDto(item);
    }

    public void deleteItem(Integer id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Ambiente não encontrado com id: " + id);
        }
        repository.deleteById(id);
        logger.info("Deletado item com id {}", id);
    }
}
