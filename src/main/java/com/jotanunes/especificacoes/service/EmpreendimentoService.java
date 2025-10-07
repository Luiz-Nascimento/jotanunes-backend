package com.jotanunes.especificacoes.service;

import com.jotanunes.especificacoes.dto.CombinacaoEMM.MaterialMarcasNomeResponse;
import com.jotanunes.especificacoes.dto.empreendimento.EmpreendimentoRequest;
import com.jotanunes.especificacoes.dto.empreendimento.EmpreendimentoResponse;
import com.jotanunes.especificacoes.exception.ResourceNotFoundException;
import com.jotanunes.especificacoes.mapper.EmpreendimentoMapper;
import com.jotanunes.especificacoes.model.CombinacaoEMM;
import com.jotanunes.especificacoes.model.Empreendimento;
import com.jotanunes.especificacoes.repository.CombinacaoEMMRepository;
import com.jotanunes.especificacoes.repository.EmpreendimentoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class EmpreendimentoService {

    private static final Logger logger = LoggerFactory.getLogger(EmpreendimentoService.class);

    private final EmpreendimentoRepository empreendimentoRepository;

    private final EmpreendimentoMapper empreendimentoMapper;

    @Autowired
    CombinacaoEMMRepository combinacaoEMMRepository;
    @Autowired
    private CombinacaoEMMService combinacaoEMMService;

    public EmpreendimentoService(EmpreendimentoRepository empreendimentoRepository,
                                 EmpreendimentoMapper empreendimentoMapper) {
        this.empreendimentoRepository =  empreendimentoRepository;
        this.empreendimentoMapper = empreendimentoMapper;
    }

    public List<EmpreendimentoResponse> findAll() {
        return empreendimentoRepository.findAll().stream()
                .map(e -> empreendimentoMapper.toDto(e, combinacaoEMMService.findMaterialMarcasNomeByEmpreendimentoId(e.getId())))
                .toList();
    }

    public EmpreendimentoResponse findById(Integer id) {
        Empreendimento empreendimento = empreendimentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empreedimento não encontrado com id: " + id));
        List<MaterialMarcasNomeResponse> marcas = combinacaoEMMService.findMaterialMarcasNomeByEmpreendimentoId(id);
        return empreendimentoMapper.toDto(empreendimento, marcas);
    }

    public EmpreendimentoResponse create(EmpreendimentoRequest data) {
        Empreendimento empreendimentoPersistido = empreendimentoRepository.save(empreendimentoMapper.requestToEntity(data));
        logger.info("Empreendimento criado com id: {}", empreendimentoPersistido.getId());
        List<MaterialMarcasNomeResponse> marcas = combinacaoEMMService.findMaterialMarcasNomeByEmpreendimentoId(empreendimentoPersistido.getId());
        return empreendimentoMapper.toDto(empreendimentoPersistido, marcas);
    }

    public void delete(Integer id) {
        if (!empreendimentoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Empreendimento não encontrado com id: "+ id);
        }
        empreendimentoRepository.deleteById(id);
    }
}
