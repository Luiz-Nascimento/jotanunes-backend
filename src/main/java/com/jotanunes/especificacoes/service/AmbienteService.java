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

    public AmbienteService(AmbienteRepository ambienteRepository, AmbienteMapper ambienteMapper, EmpreendimentoRepository empreendimentoRepository, ItemMapper itemMapper, StatusVerifyCascadeUtil statusVerifyCascadeUtil, CatalogoAmbienteRepository catalogoAmbienteRepository, CatalogoItemRepository catalogoItemRepository) {
        this.ambienteRepository = ambienteRepository;
        this.ambienteMapper = ambienteMapper;
        this.empreendimentoRepository = empreendimentoRepository;
        this.itemMapper = itemMapper;
        this.statusVerifyCascadeUtil = statusVerifyCascadeUtil;
        this.catalogoAmbienteRepository = catalogoAmbienteRepository;
        this.catalogoItemRepository = catalogoItemRepository;
    }

    public List<AmbienteResponse> getAllAmbientes() {
        return ambienteMapper.toDtoList(ambienteRepository.findAll());
    }

    public AmbienteResponse getAmbienteById(Integer id) {
        return ambienteMapper.toDto(findAmbienteOrThrow(id));
    }
    @Transactional
    public AmbienteDocResponse getAmbienteDocResponse(Integer id) {
        return ambienteMapper.toDocResponse(findAmbienteOrThrow(id));
    }
    @Transactional
    public List<ItemResponse> getItensByAmbienteId(Integer id) {
        Ambiente ambiente = findAmbienteOrThrow(id);
        return ambiente.getItens().stream().map(itemMapper::toDto).toList();
    }

    @Transactional
    public AmbienteResponse createAmbienteVazio(AmbienteRequest data) {
        Empreendimento empreendimento = findEmpreendimentoOrThrow(data.idEmpreendimento());
        CatalogoAmbiente ambienteModelo = findCatalogoAmbienteOrThrow(data.idCatalogoAmbiente());

        Ambiente ambiente = buildBaseAmbiente(empreendimento, ambienteModelo);

        Ambiente ambienteSalvo = ambienteRepository.save(ambiente);
        saveAndPostProcess(ambienteSalvo);

        logger.info("Ambiente criado com id {} no empreendimento: {} ", ambienteSalvo.getId(), empreendimento.getId());
        return ambienteMapper.toDto(ambienteSalvo);
    }

    @Transactional
    public AmbienteResponse createAmbienteModelo(AmbienteRequest data) {
        Empreendimento empreendimento = findEmpreendimentoOrThrow(data.idEmpreendimento());
        CatalogoAmbiente ambienteModelo = findCatalogoAmbienteOrThrow(data.idCatalogoAmbiente());

        Ambiente ambiente = buildBaseAmbiente(empreendimento, ambienteModelo);

        // Preencher itens do ambiente com base no catálogo
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
        saveAndPostProcess(ambienteSalvo);

        logger.info("Ambiente criado com id {} no empreendimento: {} ", ambienteSalvo.getId(), empreendimento.getId());

        return ambienteMapper.toDto(ambienteSalvo);

    }

    public void deleteAmbiente(Integer id) {
        if (!ambienteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Ambiente não encontrado com id: " + id);
        }
        ambienteRepository.deleteById(id);
        logger.info("Ambiente deletado com id: {}", id);
    }

    // -------------------------- Helpers (refatoração para clareza) --------------------------
    private Ambiente findAmbienteOrThrow(Integer id) {
        return ambienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ambiente não encontrado com id: " + id));
    }

    private Empreendimento findEmpreendimentoOrThrow(Integer id) {
        return empreendimentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empreendimento não encontrado com o id: " + id));
    }

    private CatalogoAmbiente findCatalogoAmbienteOrThrow(Integer id) {
        return catalogoAmbienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Catálogo de Ambiente não encontrado com o id: " + id));
    }

    private Ambiente buildBaseAmbiente(Empreendimento empreendimento, CatalogoAmbiente ambienteModelo) {
        Ambiente ambiente = new Ambiente();
        ambiente.setEmpreendimento(empreendimento);
        ambiente.setCatalogoAmbiente(ambienteModelo);
        return ambiente;
    }

    private void saveAndPostProcess(Ambiente ambienteSalvo) {
        // atualizar status em cascade usando a entidade salva (com id)
        statusVerifyCascadeUtil.atualizarStatusCascade(ambienteSalvo);
        // caso precise salvar itens ou executar lógica adicional, fazê-lo aqui.
    }

}
