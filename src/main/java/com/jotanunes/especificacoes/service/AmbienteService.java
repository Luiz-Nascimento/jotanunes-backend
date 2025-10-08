package com.jotanunes.especificacoes.service;


import com.jotanunes.especificacoes.dto.ambiente.AmbienteDocResponse;
import com.jotanunes.especificacoes.dto.ambiente.AmbienteRequest;
import com.jotanunes.especificacoes.dto.ambiente.AmbienteResponse;
import com.jotanunes.especificacoes.exception.ResourceNotFoundException;
import com.jotanunes.especificacoes.mapper.AmbienteMapper;
import com.jotanunes.especificacoes.model.Ambiente;
import com.jotanunes.especificacoes.model.Empreendimento;
import com.jotanunes.especificacoes.repository.AmbienteRepository;
import com.jotanunes.especificacoes.repository.EmpreendimentoRepository;
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

    public AmbienteService(AmbienteRepository ambienteRepository, AmbienteMapper ambienteMapper, EmpreendimentoRepository empreendimentoRepository) {
        this.ambienteRepository = ambienteRepository;
        this.ambienteMapper = ambienteMapper;
        this.empreendimentoRepository = empreendimentoRepository;
    }

    public List<AmbienteResponse> findAll() {
        return ambienteRepository.findAll().stream().map(ambienteMapper::toDto).toList();
    }

    public AmbienteResponse findById(Integer id) {
        return ambienteMapper.toDto(ambienteRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Ambiente não encontrado com id: " + id)));
    }
    public AmbienteDocResponse getDocResponse(Integer id) {
        return ambienteMapper.toDocResponse(ambienteRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Ambiente não encontrado com id: " + id)));
    }

    @Transactional
    public AmbienteResponse create(AmbienteRequest data, Integer id) {
        Empreendimento empreendimento = empreendimentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empreendimento não encontrado com o id: " + id));
        Ambiente ambiente = ambienteMapper.toEntity(data);
        ambiente.setEmpreendimento(empreendimento);
        Ambiente ambienteSalvo = ambienteRepository.save(ambiente);
        empreendimento.getAmbientes().add(ambienteSalvo);
        logger.info("Ambiente criado com id {} no empreendimento: {} ", ambiente.getId(), empreendimento.getId());
        return ambienteMapper.toDto(ambienteSalvo);
    }

    public void delete(Integer id) {
        if (!ambienteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Ambiente não encontrado com id: " + id);
        }
        ambienteRepository.deleteById(id);
        logger.info("Ambiente deletado com id: {}", id);
    }

}
