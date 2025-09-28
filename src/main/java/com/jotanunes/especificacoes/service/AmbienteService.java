package com.jotanunes.especificacoes.service;


import com.jotanunes.especificacoes.dto.ambiente.AmbienteRequest;
import com.jotanunes.especificacoes.dto.ambiente.AmbienteResponse;
import com.jotanunes.especificacoes.dto.empreendimento.EmpreendimentoRequest;
import com.jotanunes.especificacoes.mapper.AmbienteMapper;
import com.jotanunes.especificacoes.model.Ambiente;
import com.jotanunes.especificacoes.model.Empreendimento;
import com.jotanunes.especificacoes.repository.AmbienteRepository;
import com.jotanunes.especificacoes.repository.EmpreendimentoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AmbienteService {

    @Autowired
    private AmbienteRepository ambienteRepository;

    @Autowired
    private AmbienteMapper ambienteMapper;
    @Autowired
    private EmpreendimentoRepository empreendimentoRepository;

    public List<AmbienteResponse> findAll() {
        return ambienteRepository.findAll().stream().map(a -> ambienteMapper.toDto(a)).toList();
    }

    @Transactional
    public AmbienteResponse create(AmbienteRequest data) {
        Empreendimento empreendimento = empreendimentoRepository.findById(data.idEmpreendimento())
                .orElseThrow(() -> new EntityNotFoundException("Empreendimento não encontrado com o id: " + data.idEmpreendimento()));
        Ambiente ambiente = ambienteMapper.toEntity(data);
        ambiente.setEmpreendimento(empreendimento);
        ambienteRepository.save(ambiente);
        empreendimento.getAmbientes().add(ambiente);
        return ambienteMapper.toDto(ambiente);
    }

}
