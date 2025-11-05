package com.jotanunes.especificacoes.service;

import com.jotanunes.especificacoes.dto.CombinacaoEMM.MaterialMarcasNomeResponse;
import com.jotanunes.especificacoes.dto.ambiente.AmbienteResponse;
import com.jotanunes.especificacoes.dto.empreendimento.EmpreendimentoDocResponse;
import com.jotanunes.especificacoes.dto.empreendimento.EmpreendimentoRequest;
import com.jotanunes.especificacoes.dto.empreendimento.EmpreendimentoResponse;
import com.jotanunes.especificacoes.dto.empreendimento.EmpreendimentoUpdate;
import com.jotanunes.especificacoes.enums.AmbienteStatus;
import com.jotanunes.especificacoes.enums.EmpreendimentoStatus;
import com.jotanunes.especificacoes.enums.ItemStatus;
import com.jotanunes.especificacoes.exception.EmpreendimentoNotApprovedException;
import com.jotanunes.especificacoes.exception.ResourceNotFoundException;
import com.jotanunes.especificacoes.mapper.AmbienteMapper;
import com.jotanunes.especificacoes.mapper.EmpreendimentoMapper;
import com.jotanunes.especificacoes.model.Ambiente;
import com.jotanunes.especificacoes.model.CombinacaoEMM;
import com.jotanunes.especificacoes.model.Empreendimento;
import com.jotanunes.especificacoes.model.Item;
import com.jotanunes.especificacoes.repository.EmpreendimentoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class EmpreendimentoService {

    private static final Logger logger = LoggerFactory.getLogger(EmpreendimentoService.class);

    private final EmpreendimentoRepository empreendimentoRepository;
    private final EmpreendimentoMapper empreendimentoMapper;
    private final CombinacaoEMMService combinacaoEMMService;
    private final AmbienteMapper ambienteMapper;
    private final ItemService itemService;

    public EmpreendimentoService(EmpreendimentoRepository empreendimentoRepository, EmpreendimentoMapper empreendimentoMapper, CombinacaoEMMService combinacaoEMMService, AmbienteMapper ambienteMapper, ItemService itemService) {
        this.empreendimentoRepository = empreendimentoRepository;
        this.empreendimentoMapper = empreendimentoMapper;
        this.combinacaoEMMService = combinacaoEMMService;
        this.ambienteMapper = ambienteMapper;
        this.itemService = itemService;
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
        Set<Ambiente> ambientesCopiados = new HashSet<>();
        for (Ambiente ambiente: empreendimentoReferencia.getAmbientes()) {
            ambientesCopiados.add(new Ambiente(ambiente, empreendimentoMapped));
        }
        empreendimentoMapped.setAmbientes(ambientesCopiados);
        // Copia lista de EMM do empreendimento referência
        Set<CombinacaoEMM> combinacoesCopiadas = new HashSet<>();
        for (CombinacaoEMM combinacao: empreendimentoReferencia.getMateriaisPorMarca()) {
            combinacoesCopiadas.add(new CombinacaoEMM(empreendimentoMapped, combinacao.getMaterial(), combinacao.getMarca()));
        }
        empreendimentoMapped.setMateriaisPorMarca(combinacoesCopiadas);
        Empreendimento empreendimentoSalvo = empreendimentoRepository.save(empreendimentoMapped);
        // retorna novo empreendimento copiado
        return empreendimentoMapper.toDto(empreendimentoSalvo);
    }
    @Transactional
    public void aprovarEmpreendimento(Integer idEmpreendimento) {
        Empreendimento empreendimento = empreendimentoRepository.findById(idEmpreendimento)
                .orElseThrow(() -> new ResourceNotFoundException("Empreendimento não encontrado com id: " + idEmpreendimento));
        // Para cada ambiente do empreendimento
        empreendimento.setStatus(EmpreendimentoStatus.APROVADO);
        for (Ambiente ambiente: empreendimento.getAmbientes()) {
            // Para cada item do ambiente
            ambiente.setStatus(AmbienteStatus.APROVADO);
            for (Item item: ambiente.getItens()) {
                // Aprovar item
                item.setStatus(ItemStatus.APROVADO);
            }
        }
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
