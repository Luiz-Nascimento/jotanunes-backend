package com.jotanunes.especificacoes.service;

import com.jotanunes.especificacoes.dto.empreendimento.EmpreendimentoRequest;
import com.jotanunes.especificacoes.dto.empreendimento.EmpreendimentoResponse;
import com.jotanunes.especificacoes.exception.ResourceNotFoundException;
import com.jotanunes.especificacoes.mapper.EmpreendimentoMapper;
import com.jotanunes.especificacoes.model.Empreendimento;
import com.jotanunes.especificacoes.repository.EmpreendimentoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpreendimentoService {

    private static final Logger logger = LoggerFactory.getLogger(EmpreendimentoService.class);

    private final EmpreendimentoRepository empreendimentoRepository;

    private final EmpreendimentoMapper empreendimentoMapper;

    public EmpreendimentoService(EmpreendimentoRepository empreendimentoRepository,
                                 EmpreendimentoMapper empreendimentoMapper) {
        this.empreendimentoRepository =  empreendimentoRepository;
        this.empreendimentoMapper = empreendimentoMapper;
    }

    public List<EmpreendimentoResponse> findAll() {
        return empreendimentoRepository.findAll().stream()
                .map(empreendimentoMapper::toDto)
                .toList();
    }

    public EmpreendimentoResponse findById(Integer id) {
        Empreendimento empreendimento = empreendimentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empreedimento não encontrado com id: " + id));
        return empreendimentoMapper.toDto(empreendimento);
    }

    public EmpreendimentoResponse create(EmpreendimentoRequest data) {
        Empreendimento empreendimentoPersistido = empreendimentoRepository.save(empreendimentoMapper.requestToEntity(data));
        logger.info("Empreendimento criado com id: {}", empreendimentoPersistido.getId());
        return empreendimentoMapper.toDto(empreendimentoPersistido);
    }

    public void delete(Integer id) {
        if (!empreendimentoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Empreendimento não encontrado com id: "+ id);
        }
        empreendimentoRepository.deleteById(id);
    }
}
