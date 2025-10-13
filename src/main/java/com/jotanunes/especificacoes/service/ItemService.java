package com.jotanunes.especificacoes.service;

import com.jotanunes.especificacoes.dto.item.ItemDocResponse;
import com.jotanunes.especificacoes.dto.item.ItemRequest;
import com.jotanunes.especificacoes.dto.item.ItemResponse;
import com.jotanunes.especificacoes.dto.revisaoItens.RevisaoItemRequest;
import com.jotanunes.especificacoes.dto.revisaoItens.RevisaoItemResponse;
import com.jotanunes.especificacoes.exception.ResourceNotFoundException;
import com.jotanunes.especificacoes.mapper.ItemMapper;
import com.jotanunes.especificacoes.mapper.RevisaoItemMapper;
import com.jotanunes.especificacoes.model.Ambiente;
import com.jotanunes.especificacoes.model.Item;
import com.jotanunes.especificacoes.model.RevisaoItem;
import com.jotanunes.especificacoes.model.Usuario;
import com.jotanunes.especificacoes.repository.AmbienteRepository;
import com.jotanunes.especificacoes.repository.ItemRepository;
import com.jotanunes.especificacoes.repository.RevisaoItemRepository;
import com.jotanunes.especificacoes.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ItemService {

    private final ItemRepository repository;
    private final ItemMapper mapper;
    private final AmbienteRepository ambienteRepository;
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    RevisaoItemRepository revisaoItemRepository;

    private final Logger logger = LoggerFactory.getLogger(ItemService.class);
    @Autowired
    private RevisaoItemMapper revisaoItemMapper;

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

    @Transactional
    public RevisaoItemResponse reviewItem(Integer id, RevisaoItemRequest data) {
        Item item = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item não encontrado com id: " + id));
        if (item.getStatus() == com.jotanunes.especificacoes.enums.ItemStatus.APROVADO ||
            item.getStatus() == com.jotanunes.especificacoes.enums.ItemStatus.REPROVADO) {
            throw new IllegalStateException("Item já foi revisado com status: " + item.getStatus());
        }

        if (data.status() == com.jotanunes.especificacoes.enums.ItemStatus.REPROVADO &&
                data.motivo() == null) {
            throw new IllegalArgumentException("Motivo é obrigatório para itens reprovados.");
        }
        // Lógica para buscar informações do usuário que está revisando o item
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com email: " + email));
        item.setStatus(data.status());
        RevisaoItem revisao = new RevisaoItem(item, data.status(), data.motivo(), usuario);
        RevisaoItem revisaoSalva = revisaoItemRepository.save(revisao);
        logger.info("Item com id {} revisado para o status {} por usuario {}", id, data.status(), email);
        return revisaoItemMapper.toDto(revisaoSalva);
    }

    public void deleteItem(Integer id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Ambiente não encontrado com id: " + id);
        }
        repository.deleteById(id);
        logger.info("Deletado item com id {}", id);
    }
}
