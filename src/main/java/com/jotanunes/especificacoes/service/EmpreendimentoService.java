package com.jotanunes.especificacoes.service;

import com.jotanunes.especificacoes.dto.CombinacaoEMM.MaterialMarcasNomeResponse;
import com.jotanunes.especificacoes.dto.ambiente.AmbienteResponse;
import com.jotanunes.especificacoes.dto.empreendimento.EmpreendimentoDocResponse;
import com.jotanunes.especificacoes.dto.empreendimento.EmpreendimentoRequest;
import com.jotanunes.especificacoes.dto.empreendimento.EmpreendimentoResponse;
import com.jotanunes.especificacoes.dto.empreendimento.EmpreendimentoUpdate;
import com.jotanunes.especificacoes.enums.EmpreendimentoStatus;
import com.jotanunes.especificacoes.exception.EmpreendimentoNotApprovedException;
import com.jotanunes.especificacoes.exception.ResourceNotFoundException;
import com.jotanunes.especificacoes.mapper.AmbienteMapper;
import com.jotanunes.especificacoes.mapper.EmpreendimentoMapper;
import com.jotanunes.especificacoes.model.Ambiente;
import com.jotanunes.especificacoes.model.CombinacaoEMM;
import com.jotanunes.especificacoes.model.Empreendimento;
import com.jotanunes.especificacoes.repository.EmpreendimentoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmpreendimentoService {

    private static final Logger logger = LoggerFactory.getLogger(EmpreendimentoService.class);

    private final EmpreendimentoRepository empreendimentoRepository;
    private final EmpreendimentoMapper empreendimentoMapper;
    private final CombinacaoEMMService combinacaoEMMService;
    private final AmbienteMapper ambienteMapper;

    public EmpreendimentoService(EmpreendimentoRepository empreendimentoRepository, EmpreendimentoMapper empreendimentoMapper, CombinacaoEMMService combinacaoEMMService, AmbienteMapper ambienteMapper) {
        this.empreendimentoRepository = empreendimentoRepository;
        this.empreendimentoMapper = empreendimentoMapper;
        this.combinacaoEMMService = combinacaoEMMService;
        this.ambienteMapper = ambienteMapper;
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
        Empreendimento empreendimento = empreendimentoMapper.requestToEntity(data);
        Empreendimento empreendimentoPersistido = empreendimentoRepository.save(empreendimento);
        System.out.println(empreendimentoPersistido.getSegmento());
        logger.info("Empreendimento criado com id: {}", empreendimentoPersistido.getId());
        return empreendimentoMapper.toDto(empreendimentoPersistido);
    }

    public EmpreendimentoResponse createEmpreendimentoCopia(EmpreendimentoRequest data, Integer idEmpreendimento) {
        // Verificar se o empreendimento referência existe
        Empreendimento empreendimentoReferencia = empreendimentoRepository.findById(idEmpreendimento)
                .orElseThrow(() -> new ResourceNotFoundException("Empreendimento não encontrado com id: " + idEmpreendimento));
        // Verifica se o empreendimento referência está aprovado
        if (!empreendimentoReferencia.getStatus().equals(EmpreendimentoStatus.APROVADO)) {
            throw new EmpreendimentoNotApprovedException();
        }
        // Preenche informações do empreendimento novo
        Empreendimento empreendimentoMapped = empreendimentoMapper.requestToEntity(data);
        // Pega o conjunto de ambientes do empreendimento novo
        // Para cada ambiente dentro do conjunto de ambientes do empreendimento referencia, copiar e joga-lo no novo
        for (Ambiente ambiente: empreendimentoReferencia.getAmbientes()) {
            empreendimentoMapped.getAmbientes().add(new Ambiente(ambiente, empreendimentoMapped));
        }
        // Copia lista de EMM do empreendimento referência
        for (CombinacaoEMM combinacao: empreendimentoReferencia.getMateriaisPorMarca()) {
            empreendimentoMapped.getMateriaisPorMarca().add(new CombinacaoEMM(empreendimentoMapped, combinacao.getMaterial(), combinacao.getMarca()));
        }

        Empreendimento empreendimentoSalvo = empreendimentoRepository.save(empreendimentoMapped);
        // retorna novo empreendimento copiado
        return empreendimentoMapper.toDto(empreendimentoSalvo);
    }

    @Transactional
    public EmpreendimentoResponse updateEmpreendimento(EmpreendimentoUpdate data) {
        Empreendimento empreendimentoExistente = empreendimentoRepository.findById(data.idEmpreendimento())
                .orElseThrow(() -> new ResourceNotFoundException("Empreendimento não encontrado com id: " + data.idEmpreendimento()));
        empreendimentoMapper.updateFromDto(data, empreendimentoExistente);
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
