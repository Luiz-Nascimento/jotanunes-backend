package com.jotanunes.especificacoes.service;

import com.jotanunes.especificacoes.dto.item.ItemRequest;
import com.jotanunes.especificacoes.dto.item.ItemResponse;
import com.jotanunes.especificacoes.mapper.ItemMapper;
import com.jotanunes.especificacoes.model.Ambiente;
import com.jotanunes.especificacoes.model.Item;
import com.jotanunes.especificacoes.repository.AmbienteRepository;
import com.jotanunes.especificacoes.repository.ItemRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ItemService {

    @Autowired
    private ItemRepository repository;

    @Autowired
    private ItemMapper mapper;
    @Autowired
    private AmbienteRepository ambienteRepository;

    public List<ItemResponse> findAll() {
        return repository.findAll().stream().map(item -> mapper.toDto(item)).toList();
    }

    public ItemResponse findById(Integer id) {
        return mapper.toDto(repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item nao encontrado com id: " + id)));
    }

    @Transactional
    public ItemResponse create(ItemRequest data, Integer id) {
        Ambiente ambiente = ambienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ambiente nao encontrado com id: " + id));
        Item item = mapper.toEntity(data);
        item.setAmbiente(ambiente);
        repository.save(item);
        ambiente.getItens().add(item);
        return mapper.toDto(item);
    }

    public void delete(Integer id) {
        Item item = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item nao encontrado com id: " + id));
        repository.deleteById(id);
    }
}
