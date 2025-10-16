package com.jotanunes.especificacoes.service;


import com.jotanunes.especificacoes.dto.ambiente.AmbienteDocResponse;
import com.jotanunes.especificacoes.dto.ambiente.AmbienteRequest;
import com.jotanunes.especificacoes.dto.ambiente.AmbienteResponse;
import com.jotanunes.especificacoes.dto.item.ItemResponse;
import com.jotanunes.especificacoes.exception.ResourceNotFoundException;
import com.jotanunes.especificacoes.mapper.AmbienteMapper;
import com.jotanunes.especificacoes.mapper.ItemMapper;
import com.jotanunes.especificacoes.model.Ambiente;
import com.jotanunes.especificacoes.model.CatalogoAmbiente;
import com.jotanunes.especificacoes.model.Empreendimento;
import com.jotanunes.especificacoes.model.Item;
import com.jotanunes.especificacoes.repository.*;
import com.jotanunes.especificacoes.util.StatusVerifyCascadeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AmbienteService {

    private final AmbienteRepository ambienteRepository;
    private final AmbienteMapper ambienteMapper;
    private final EmpreendimentoRepository empreendimentoRepository;

    private final Logger logger = LoggerFactory.getLogger(AmbienteService.class);
    private final ItemMapper itemMapper;
    private final StatusVerifyCascadeUtil statusVerifyCascadeUtil;
    private final CatalogoAmbienteRepository catalogoAmbienteRepository;
    private final CatalogoItemRepository catalogoItemRepository;
    private final ItemRepository itemRepository;

    public AmbienteService(AmbienteRepository ambienteRepository, AmbienteMapper ambienteMapper, EmpreendimentoRepository empreendimentoRepository, ItemMapper itemMapper, StatusVerifyCascadeUtil statusVerifyCascadeUtil, CatalogoAmbienteRepository catalogoAmbienteRepository, CatalogoItemRepository catalogoItemRepository, ItemRepository itemRepository) {
        this.ambienteRepository = ambienteRepository;
        this.ambienteMapper = ambienteMapper;
        this.empreendimentoRepository = empreendimentoRepository;
        this.itemMapper = itemMapper;
        this.statusVerifyCascadeUtil = statusVerifyCascadeUtil;
        this.catalogoAmbienteRepository = catalogoAmbienteRepository;
        this.catalogoItemRepository = catalogoItemRepository;
        this.itemRepository = itemRepository;
    }

    public List<AmbienteResponse> getAllAmbientes() {
        return ambienteRepository.findAll().stream().map(ambienteMapper::toDto).toList();
    }

    public AmbienteResponse getAmbienteById(Integer id) {
        return ambienteMapper.toDto(ambienteRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Ambiente não encontrado com id: " + id)));
    }
    public AmbienteDocResponse getAmbienteDocResponse(Integer id) {
        return ambienteMapper.toDocResponse(ambienteRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Ambiente não encontrado com id: " + id)));
    }

    public List<ItemResponse> getItensByAmbienteId(Integer id) {
        Ambiente ambiente = ambienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ambiente não encontrado com id: " + id));
        return ambiente.getItens().stream().map(itemMapper::toDto).toList();
    }

    @Transactional
    public AmbienteResponse createAmbienteVazio(AmbienteRequest data) {
        Empreendimento empreendimento = empreendimentoRepository.findById(data.idEmpreendimento())
                .orElseThrow(() -> new ResourceNotFoundException("Empreendimento não encontrado com o id: " + data.idEmpreendimento()));
        CatalogoAmbiente ambienteModelo = catalogoAmbienteRepository.findById(data.idCatalogoAmbiente())
                .orElseThrow(() -> new ResourceNotFoundException("Catálogo de Ambiente não encontrado com o id: " + data.idCatalogoAmbiente()));
        Ambiente ambiente = new Ambiente();
        ambiente.setEmpreendimento(empreendimento);
        ambiente.setCatalogoAmbiente(ambienteModelo);
        Ambiente ambienteSalvo = ambienteRepository.save(ambiente);
        statusVerifyCascadeUtil.atualizarStatusCascade(ambiente);
        logger.info("Ambiente criado com id {} no empreendimento: {} ", ambiente.getId(), empreendimento.getId());
        return ambienteMapper.toDto(ambienteSalvo);
    }

    @Transactional
    public AmbienteResponse createAmbienteModelo(AmbienteRequest data) {
        Empreendimento empreendimento = empreendimentoRepository.findById(data.idEmpreendimento())
                .orElseThrow(() -> new ResourceNotFoundException("Empreendimento não encontrado com o id: " + data.idEmpreendimento()));
        CatalogoAmbiente ambienteModelo = catalogoAmbienteRepository.findById(data.idCatalogoAmbiente())
                .orElseThrow(() -> new ResourceNotFoundException("Catálogo de Ambiente não encontrado com o id: " + data.idCatalogoAmbiente()));
        Ambiente ambiente = new Ambiente();
        ambiente.setEmpreendimento(empreendimento);
        ambiente.setCatalogoAmbiente(ambienteModelo);
        // Preencher itens do ambiente com base no catálogo
        // Vou pegar a lista de itens do ambiente
        // Chamar o conteudo usando o repository de catalogo de itens, baseado no id do ambienteModelo
        // mapeando para formato de item e adicionando na lista de itens do ambiente

        List<Item> itensModelo = catalogoItemRepository.findByAmbienteId(ambienteModelo.getId())
                .stream()
                .map(catalogoItem -> {
                    Item item = new Item();
                    item.setCatalogoItem(catalogoItem);
                    item.setAmbiente(ambiente);
                    return item;
                })
                .toList();

        ambiente.getItens().addAll(itensModelo);
        Ambiente ambienteSalvo = ambienteRepository.save(ambiente);

        statusVerifyCascadeUtil.atualizarStatusCascade(ambiente);
        logger.info("Ambiente criado com id {} no empreendimento: {} ", ambiente.getId(), empreendimento.getId());

        return ambienteMapper.toDto(ambienteSalvo);

    }

    public void deleteAmbiente(Integer id) {
        if (!ambienteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Ambiente não encontrado com id: " + id);
        }
        ambienteRepository.deleteById(id);
        logger.info("Ambiente deletado com id: {}", id);
    }

}
