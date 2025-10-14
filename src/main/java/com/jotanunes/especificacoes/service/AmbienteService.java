package com.jotanunes.especificacoes.service;


import com.jotanunes.especificacoes.dto.ambiente.AmbienteDocResponse;
import com.jotanunes.especificacoes.dto.ambiente.AmbienteRequest;
import com.jotanunes.especificacoes.dto.ambiente.AmbienteResponse;
import com.jotanunes.especificacoes.dto.item.ItemResponse;
import com.jotanunes.especificacoes.enums.ItemStatus;
import com.jotanunes.especificacoes.exception.ResourceNotFoundException;
import com.jotanunes.especificacoes.mapper.AmbienteMapper;
import com.jotanunes.especificacoes.mapper.ItemMapper;
import com.jotanunes.especificacoes.model.Ambiente;
import com.jotanunes.especificacoes.model.Empreendimento;
import com.jotanunes.especificacoes.model.Item;
import com.jotanunes.especificacoes.repository.AmbienteRepository;
import com.jotanunes.especificacoes.repository.EmpreendimentoRepository;
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

    public AmbienteService(AmbienteRepository ambienteRepository, AmbienteMapper ambienteMapper, EmpreendimentoRepository empreendimentoRepository, ItemMapper itemMapper, StatusVerifyCascadeUtil statusVerifyCascadeUtil) {
        this.ambienteRepository = ambienteRepository;
        this.ambienteMapper = ambienteMapper;
        this.empreendimentoRepository = empreendimentoRepository;
        this.itemMapper = itemMapper;
        this.statusVerifyCascadeUtil = statusVerifyCascadeUtil;
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
    public AmbienteResponse createAmbiente(AmbienteRequest data) {
        Empreendimento empreendimento = empreendimentoRepository.findById(data.idEmpreendimento())
                .orElseThrow(() -> new ResourceNotFoundException("Empreendimento não encontrado com o id: " + data.idEmpreendimento()));
        Ambiente ambiente = ambienteMapper.toEntity(data);
        ambiente.setEmpreendimento(empreendimento);
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
