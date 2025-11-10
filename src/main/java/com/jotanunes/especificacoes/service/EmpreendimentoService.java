package com.jotanunes.especificacoes.service;

import com.jotanunes.especificacoes.dto.empreendimento.*;
import com.jotanunes.especificacoes.enums.AmbienteStatus;
import com.jotanunes.especificacoes.enums.EmpreendimentoStatus;
import com.jotanunes.especificacoes.enums.ItemStatus;
import com.jotanunes.especificacoes.exception.EmpreendimentoNotApprovedException;
import com.jotanunes.especificacoes.exception.ResourceNotFoundException;
import com.jotanunes.especificacoes.mapper.AmbienteMapper;
import com.jotanunes.especificacoes.mapper.EmpreendimentoMapper;
import com.jotanunes.especificacoes.model.*;
import com.jotanunes.especificacoes.repository.EmpreendimentoRepository;
import com.jotanunes.especificacoes.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final UserRepository userRepository;
    private final AmbienteMapper ambienteMapper;

    public EmpreendimentoService(EmpreendimentoRepository empreendimentoRepository, UserRepository userRepository, EmpreendimentoMapper empreendimentoMapper, CombinacaoEMMService combinacaoEMMService, AmbienteMapper ambienteMapper) {
        this.empreendimentoRepository = empreendimentoRepository;
        this.userRepository = userRepository;
        this.empreendimentoMapper = empreendimentoMapper;
        this.combinacaoEMMService = combinacaoEMMService;
        this.ambienteMapper = ambienteMapper;
    }



    public List<EmpreendimentoResponse> findAll() {
        return empreendimentoMapper.toDtoList(empreendimentoRepository.findAll());
    }

    public EmpreendimentoResponse findById(Integer id) {
        return empreendimentoMapper.toDto(empreendimentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empreendimento não encontrado com id: " + id)));
    }

    @Transactional(readOnly = true)
    public EspecificacaTecnicaDTO getDadosParaRelatorio(Integer id) {
        Empreendimento empreendimento = empreendimentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empreendimento não encontrado com id: " + id));
        return empreendimentoMapper.toEspecificacaoTecnica(empreendimento);
    }


    public EmpreendimentoResponse create(EmpreendimentoRequest data) {
        Empreendimento empreendimento = empreendimentoMapper.requestToEntity(data);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User usuario = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        empreendimento.setCriadoPor(usuario);
        Empreendimento empreendimentoPersistido = empreendimentoRepository.save(empreendimento);
        System.out.println(empreendimentoPersistido.getSegmento());
        logger.info("Empreendimento criado com id: {}", empreendimentoPersistido.getId());
        return empreendimentoMapper.toDto(empreendimentoPersistido);
    }
    public EmpreendimentoResponse copy(EmpreendimentoRequest data, Integer idEmpreendimento) {
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
    public void forceAprovacao(Integer idEmpreendimento) {
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
    public EmpreendimentoResponse update(Integer id, EmpreendimentoUpdate data) {
        Empreendimento empreendimentoExistente = empreendimentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empreendimento não encontrado com id: " + id));
        empreendimentoMapper.updateFromDto(data, empreendimentoExistente);
        Empreendimento empreendimentoAtualizado = empreendimentoRepository.save(empreendimentoExistente);
        logger.info("Empreendimento atualizado com id: {}", empreendimentoAtualizado.getId());

        return empreendimentoMapper.toDto(empreendimentoAtualizado);
    }

    @Transactional
    public EmpreendimentoResponse adicionarObservacao(Integer id, EmpreendimentoObservacao data) {
        Empreendimento empreendimento = empreendimentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empreendimento não encontrado com id: " + id));
        empreendimento.getObservacoes().add(data.observacao());
        return empreendimentoMapper.toDto(empreendimento);
    }

    public void delete(Integer id) {
        if (!empreendimentoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Empreendimento não encontrado com id: " + id);
        }
        empreendimentoRepository.deleteById(id);
    }


}