package com.jotanunes.especificacoes.service;

import com.jotanunes.especificacoes.dto.item.ItemDocResponse;
import com.jotanunes.especificacoes.dto.item.ItemRequest;
import com.jotanunes.especificacoes.dto.item.ItemResponse;
import com.jotanunes.especificacoes.dto.item.ItemUpdate;
import com.jotanunes.especificacoes.dto.revisaoItens.RevisaoItemRequest;
import com.jotanunes.especificacoes.dto.revisaoItens.RevisaoItemResponse;
import com.jotanunes.especificacoes.enums.ItemStatus;
import com.jotanunes.especificacoes.exception.ResourceNotFoundException;
import com.jotanunes.especificacoes.mapper.ItemMapper;
import com.jotanunes.especificacoes.mapper.RevisaoItemMapper;
import com.jotanunes.especificacoes.model.*;
import com.jotanunes.especificacoes.repository.*;
import com.jotanunes.especificacoes.util.AtualizadorStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;
    private final AmbienteRepository ambienteRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RevisaoItemRepository revisaoItemRepository;
    @Autowired
    AtualizadorStatus verifyCascadeUtil;

    private final Logger logger = LoggerFactory.getLogger(ItemService.class);
    @Autowired
    private RevisaoItemMapper revisaoItemMapper;
    @Autowired
    private CatalogoItemRepository catalogoItemRepository;

    public ItemService(ItemRepository itemRepository, ItemMapper itemMapper, AmbienteRepository ambienteRepository) {
        this.itemRepository = itemRepository;
        this.itemMapper = itemMapper;
        this.ambienteRepository = ambienteRepository;
    }

    public Page<ItemResponse> findAll(Pageable pageable) {
        Page<Item> itens = itemRepository.findAll(pageable);
        return itens.map(itemMapper::toDto);
    }

    public ItemResponse findById(Integer id) {
        return itemMapper.toDto(itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item nao encontrado com id: " + id)));
    }

    @Transactional
    public ItemDocResponse findByIdAsDocument(Integer id) {
        return itemMapper.toDocResponse(itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item não encontrado com id: " + id)));
    }

    @Transactional
    public List<ItemResponse> findByAmbienteId(Integer id) {
        if(!ambienteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Ambiente não encontrado com id: " + id);
        }
        return itemMapper.toDtoList(itemRepository.findByAmbienteId(id));
    }

    @Transactional
    public ItemResponse create(ItemRequest data) {
        Ambiente ambiente = ambienteRepository.findById(data.idAmbiente())
                .orElseThrow(() -> new ResourceNotFoundException("Ambiente nao encontrado com id: " + data.idAmbiente()));
        CatalogoItem itemReferencia = catalogoItemRepository.findById(data.idItemCatalogo())
                .orElseThrow(() -> new ResourceNotFoundException("Item de catálogo não encontrado com id: " + data.idItemCatalogo()));
        Item item = new Item();
        item.setCatalogoItem(itemReferencia);
        item.setAmbiente(ambiente);
        Item itemSalvo = itemRepository.save(item);
        verifyCascadeUtil.atualizarStatusAmbiente(item);
        logger.info("Novo item criado com id {}, associado ao ambiente: {}", itemSalvo.getId(), data.idAmbiente());
        return itemMapper.toDto(item);
    }

    @Transactional
    public ItemResponse update(Integer id, ItemUpdate data) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item não encontrado com id: " + id));
        item.setDescricaoCustomizada(data.descricaoCustomizada());
        item.setStatus(ItemStatus.PENDENTE);
        verifyCascadeUtil.atualizarStatusAmbiente(item);
        logger.info("Item com id {} atualizado", id);
        return itemMapper.toDto(item);
    }

    @Transactional
    public RevisaoItemResponse review(RevisaoItemRequest data) {
        Item item = itemRepository.findById(data.itemId())
                .orElseThrow(() -> new ResourceNotFoundException("Item não encontrado com id: " + data.itemId()));
        if (item.getStatus() == com.jotanunes.especificacoes.enums.ItemStatus.APROVADO ||
            item.getStatus() == com.jotanunes.especificacoes.enums.ItemStatus.REPROVADO) {
            throw new IllegalStateException("Item já foi revisado com status: " + item.getStatus());
        }

        if (data.status() == com.jotanunes.especificacoes.enums.ItemStatus.REPROVADO &&
                data.motivo() == null) {
            throw new IllegalArgumentException("Motivo é obrigatório para itens reprovados.");
        }
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com email: " + email));
        item.setStatus(data.status());

        verifyCascadeUtil.atualizarStatusAmbiente(item);

        RevisaoItem revisao = new RevisaoItem(item, data.status(), data.motivo(), user);
        RevisaoItem revisaoSalva = revisaoItemRepository.save(revisao);

        logger.info("Item com id {} revisado para o status {} por usuario {}", data.itemId(), data.status(), email);
        return revisaoItemMapper.toDto(revisaoSalva);
    }

    @Transactional
    public List<RevisaoItemResponse> reviewMultiple(List<RevisaoItemRequest> revisoes) {
        return revisoes.stream().map(this::review)
                .collect(Collectors.toList());

    }

    public void delete(Integer id) {
        if (!itemRepository.existsById(id)) {
            throw new ResourceNotFoundException("Ambiente não encontrado com id: " + id);
        }
        itemRepository.deleteById(id);
        logger.info("Deletado item com id {}", id);
    }
}
