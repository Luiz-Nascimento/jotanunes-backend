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
import com.jotanunes.especificacoes.util.StatusVerifyCascadeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {

    private final ItemRepository repository;
    private final ItemMapper mapper;
    private final AmbienteRepository ambienteRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RevisaoItemRepository revisaoItemRepository;
    @Autowired
    StatusVerifyCascadeUtil verifyCascadeUtil;

    private final Logger logger = LoggerFactory.getLogger(ItemService.class);
    @Autowired
    private RevisaoItemMapper revisaoItemMapper;
    @Autowired
    private CatalogoItemRepository catalogoItemRepository;

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
    public ItemResponse createItem(ItemRequest data) {
        Ambiente ambiente = ambienteRepository.findById(data.idAmbiente())
                .orElseThrow(() -> new ResourceNotFoundException("Ambiente nao encontrado com id: " + data.idAmbiente()));
        CatalogoItem itemReferencia = catalogoItemRepository.findById(data.idItemCatalogo())
                .orElseThrow(() -> new ResourceNotFoundException("Item de catálogo não encontrado com id: " + data.idItemCatalogo()));
        Item item = new Item();
        item.setCatalogoItem(itemReferencia);
        item.setAmbiente(ambiente);
        Item itemSalvo = repository.save(item);
        verifyCascadeUtil.atualizarStatusCascade(item);
        logger.info("Novo item criado com id {}, associado ao ambiente: {}", itemSalvo.getId(), data.idAmbiente());
        return mapper.toDto(item);
    }

    @Transactional
    public ItemResponse updateItem(Integer id, ItemUpdate data) {
        Item item = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item não encontrado com id: " + id));
        item.setDescricaoCustomizada(data.descricaoCustomizada());
        item.setStatus(ItemStatus.PENDENTE);
        verifyCascadeUtil.atualizarStatusCascade(item);
        logger.info("Item com id {} atualizado", id);
        return mapper.toDto(item);
    }

    @Transactional
    public RevisaoItemResponse reviewItem(RevisaoItemRequest data) {
        Item item = repository.findById(data.itemId())
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

        verifyCascadeUtil.atualizarStatusCascade(item);

        RevisaoItem revisao = new RevisaoItem(item, data.status(), data.motivo(), user);
        RevisaoItem revisaoSalva = revisaoItemRepository.save(revisao);

        logger.info("Item com id {} revisado para o status {} por usuario {}", data.itemId(), data.status(), email);
        return revisaoItemMapper.toDto(revisaoSalva);
    }

    @Transactional
    public List<RevisaoItemResponse> reviewItemsBulk(List<RevisaoItemRequest> requests) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com email: " + email));

        List<RevisaoItem> revisoes = new ArrayList<>();
        for (RevisaoItemRequest data : requests) {
            Item item = repository.findById(data.itemId())
                    .orElseThrow(() -> new ResourceNotFoundException("Item não encontrado com id: " + data.itemId()));
            if (item.getStatus() == ItemStatus.APROVADO || item.getStatus() == ItemStatus.REPROVADO) {
                throw new IllegalStateException("Item já foi revisado com status: " + item.getStatus());
            }
            if (data.status() == ItemStatus.REPROVADO && data.motivo() == null) {
                throw new IllegalArgumentException("Motivo é obrigatório para itens reprovados.");
            }
            item.setStatus(data.status());
            revisoes.add(new RevisaoItem(item, data.status(), data.motivo(), user));
            verifyCascadeUtil.atualizarStatusCascade(item);
        }
        List<RevisaoItem> revisoesSalvas = revisaoItemRepository.saveAll(revisoes);
        revisoesSalvas.forEach(revisao ->
                logger.info("Item com id {} revisado para o status {} por usuario {}",
                        revisao.getItem().getId(), revisao.getStatus(), email)
        );
        return revisoesSalvas.stream()
                .map(revisaoItemMapper::toDto)
                .collect(Collectors.toList());
    }





    public void deleteItem(Integer id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Ambiente não encontrado com id: " + id);
        }
        repository.deleteById(id);
        logger.info("Deletado item com id {}", id);
    }
}
