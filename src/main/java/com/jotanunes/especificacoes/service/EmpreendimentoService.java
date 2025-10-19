package com.jotanunes.especificacoes.service;

import com.jotanunes.especificacoes.dto.CombinacaoEMM.MaterialMarcasNomeResponse;
import com.jotanunes.especificacoes.dto.ambiente.AmbienteResponse;
import com.jotanunes.especificacoes.dto.empreendimento.EmpreendimentoDocResponse;
import com.jotanunes.especificacoes.dto.empreendimento.EmpreendimentoRequest;
import com.jotanunes.especificacoes.dto.empreendimento.EmpreendimentoResponse;
import com.jotanunes.especificacoes.exception.ResourceNotFoundException;
import com.jotanunes.especificacoes.mapper.AmbienteMapper;
import com.jotanunes.especificacoes.mapper.EmpreendimentoMapper;
import com.jotanunes.especificacoes.model.Empreendimento;
import com.jotanunes.especificacoes.repository.CombinacaoEMMRepository;
import com.jotanunes.especificacoes.repository.EmpreendimentoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpreendimentoService {

    private static final Logger logger = LoggerFactory.getLogger(EmpreendimentoService.class);

    private final EmpreendimentoRepository empreendimentoRepository;
    private final EmpreendimentoMapper empreendimentoMapper;

    @Autowired
    private CombinacaoEMMService combinacaoEMMService;
    @Autowired
    private AmbienteMapper ambienteMapper;

    public EmpreendimentoService(EmpreendimentoRepository empreendimentoRepository,
                                 EmpreendimentoMapper empreendimentoMapper) {
        this.empreendimentoRepository = empreendimentoRepository;
        this.empreendimentoMapper = empreendimentoMapper;
    }

    public List<EmpreendimentoResponse> getAllEmpreendimentos() {
        return empreendimentoMapper.toDtoList(empreendimentoRepository.findAll());
    }

    public EmpreendimentoResponse getEmpreendimentoById(Integer id) {
        return empreendimentoMapper.toDto(empreendimentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empreendimento não encontrado com id: " + id)));
    }

    public EmpreendimentoDocResponse getEmpreendimentoDocResponse(Integer id) {
        Empreendimento empreendimento = empreendimentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empreendimento não encontrado com id: " + id));
        List<MaterialMarcasNomeResponse> marcas = combinacaoEMMService.findMaterialMarcasNomeByEmpreendimentoId(id);
        return empreendimentoMapper.toDocResponse(empreendimento, marcas);
    }

    public List<AmbienteResponse> getAmbientesByEmpreendimentoId(Integer id) {
        Empreendimento empreendimento = empreendimentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empreendimento não encontrado com id: " + id));
        return empreendimento.getAmbientes().stream()
                .map(ambienteMapper::toDto)
                .toList();
    }

    public EmpreendimentoResponse createEmpreendimento(EmpreendimentoRequest data) {
        Empreendimento empreendimento = empreendimentoRepository.save(empreendimentoMapper.requestToEntity(data));
        logger.info("Empreendimento criado com id: {}", empreendimento.getId());
        return empreendimentoMapper.toDto(empreendimento);
    }

    public EmpreendimentoResponse updateEmpreendimento(Integer id, EmpreendimentoRequest data) {
        Empreendimento empreendimentoExistente = empreendimentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empreendimento não encontrado com id: " + id));

        empreendimentoExistente.setNome(data.nome());
        empreendimentoExistente.setLocalizacao(data.localizacao());
        empreendimentoExistente.setDescricao(data.descricao());
        empreendimentoExistente.setObservacoes(data.observacoes());

        Empreendimento empreendimentoAtualizado = empreendimentoRepository.save(empreendimentoExistente);
        logger.info("Empreendimento atualizado com id: {}", empreendimentoAtualizado.getId());

        return empreendimentoMapper.toDto(empreendimentoAtualizado);
    }

    public void deleteEmpreendimento(Integer id) {
        if (!empreendimentoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Empreendimento não encontrado com id: " + id);
        }
        empreendimentoRepository.deleteById(id);
    }
}
