package com.jotanunes.especificacoes.service;

import com.jotanunes.especificacoes.dto.CombinacaoEMM.CombinacaoEMMResponse;
import com.jotanunes.especificacoes.dto.empreendimento.*;
import com.jotanunes.especificacoes.enums.AmbienteStatus;
import com.jotanunes.especificacoes.enums.EmpreendimentoStatus;
import com.jotanunes.especificacoes.enums.ItemStatus;
import com.jotanunes.especificacoes.event.EmpreendimentoPendenteEvent;
import com.jotanunes.especificacoes.exception.EmpreendimentoBusinessLogicException;
import com.jotanunes.especificacoes.exception.EmpreendimentoNotApprovedException;
import com.jotanunes.especificacoes.exception.ResourceNotFoundException;
import com.jotanunes.especificacoes.mapper.CombinacaoEMMMapper;
import com.jotanunes.especificacoes.mapper.EmpreendimentoMapper;
import com.jotanunes.especificacoes.model.*;
import com.jotanunes.especificacoes.repository.EmpreendimentoRepository;
import com.jotanunes.especificacoes.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
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
    private final UserRepository userRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final CombinacaoEMMMapper combinacaoEMMMapper;

    public EmpreendimentoService(EmpreendimentoRepository empreendimentoRepository, UserRepository userRepository, EmpreendimentoMapper empreendimentoMapper, ApplicationEventPublisher eventPublisher, CombinacaoEMMMapper combinacaoEMMMapper) {
        this.empreendimentoRepository = empreendimentoRepository;
        this.userRepository = userRepository;
        this.empreendimentoMapper = empreendimentoMapper;
        this.eventPublisher = eventPublisher;
        this.combinacaoEMMMapper = combinacaoEMMMapper;
    }

    public List<EmpreendimentoResponse> findAll() {
        return empreendimentoMapper.toDtoList(empreendimentoRepository.findAll());
    }

    @Transactional
    public EmpreendimentoResponse findById(Integer id) {
        Empreendimento empreendimento = empreendimentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empreendimento não encontrado com id: " + id));

        int totalAmbientes = empreendimento.getAmbientes().size();

        int totalItens = empreendimento.getAmbientes().stream()
                .mapToInt(a -> a.getItens().size())
                .sum();

        int totalMarcas = (int) empreendimento.getMateriaisPorMarca().stream()
                .map(CombinacaoEMM::getMarca)
                .distinct()
                .count();

        int totalMateriais = (int) empreendimento.getMateriaisPorMarca().stream()
                .map(CombinacaoEMM::getMaterial)
                .distinct()
                .count();

        return empreendimentoMapper.toDtoDetalhado(
                empreendimento,
                totalAmbientes,
                totalItens,
                totalMarcas,
                totalMateriais
        );
    }

    @Transactional(readOnly = true)
    public List<CombinacaoEMMResponse> findCombinacoes(Integer id) {
        Empreendimento empreendimento = empreendimentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empreendimento não encontrado com id: " + id));
        List<CombinacaoEMM> combinacoesEMM = empreendimento.getMateriaisPorMarca().stream().toList();
        return combinacaoEMMMapper.toDtoList(combinacoesEMM);
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
        logger.info("Empreendimento criado com id: {}", empreendimentoPersistido.getId());
        return empreendimentoMapper.toDto(empreendimentoPersistido);
    }

    @Transactional
    public void enviarParaRevisao(Integer id) {
        Empreendimento empreendimento = empreendimentoRepository.findById((id))
                .orElseThrow(() -> new ResourceNotFoundException(("Empreendimento não encontrado com id: " + id)));

        if (empreendimento.getStatus().equals(EmpreendimentoStatus.APROVADO)) {
            throw new EmpreendimentoBusinessLogicException("Empreendimento já foi aprovado, alteração bloqueada");
        }
        else if(empreendimento.getStatus() == EmpreendimentoStatus.PENDENTE) {
            throw new EmpreendimentoBusinessLogicException("Empreendimento já foi enviado para revisão!");
        }
        boolean anyAmbienteReprovado = empreendimento.getAmbientes().stream().anyMatch(
                ambiente -> ambiente.getStatus() == AmbienteStatus.REPROVADO);
        boolean anyAmbienteVazio = empreendimento.getAmbientes().stream().anyMatch(ambiente ->
                ambiente.getItens().isEmpty());
        if (anyAmbienteReprovado) {
            throw new EmpreendimentoBusinessLogicException("Empreendimento ainda não corrigido");
        }
        if (anyAmbienteVazio) {
            throw new EmpreendimentoBusinessLogicException("Empreendimento contém ambientes vazios");
        }
        empreendimento.setStatus(EmpreendimentoStatus.PENDENTE);
        eventPublisher.publishEvent(new EmpreendimentoPendenteEvent(empreendimento.getNome()));
    }

    @Transactional
    public void aprovar(Integer id) {
        Empreendimento empreendimento = empreendimentoRepository.findById((id))
                .orElseThrow(() -> new ResourceNotFoundException(("Empreendimento não encontrado com id: " + id)));

        boolean allAmbientesApproved = empreendimento.getAmbientes().stream().allMatch(
                ambiente -> ambiente.getStatus() == AmbienteStatus.APROVADO);
        if (!allAmbientesApproved) {
            throw new EmpreendimentoBusinessLogicException("Empreendimento ainda não devidamente revisado");
        }
        empreendimento.setStatus(EmpreendimentoStatus.APROVADO);
    }

    @Transactional
    public void reprovar(Integer id) {
        Empreendimento empreendimento = empreendimentoRepository.findById((id))
                .orElseThrow(() -> new ResourceNotFoundException(("Empreendimento não encontrado com id: " + id)));

        boolean anyAmbienteNaoRevisado = empreendimento.getAmbientes().stream()
                .anyMatch(ambiente -> ambiente.getStatus() == AmbienteStatus.PENDENTE);

        if (anyAmbienteNaoRevisado) {
            throw new EmpreendimentoBusinessLogicException("Empreendimento não devidamente revisado");
        }
        empreendimento.setStatus(EmpreendimentoStatus.REPROVADO);
    }

    @Transactional
    public EmpreendimentoResponse copy(EmpreendimentoRequest data, Integer idEmpreendimento) {
        Empreendimento empreendimentoReferencia = empreendimentoRepository.findById(idEmpreendimento)
                .orElseThrow(() -> new ResourceNotFoundException("Empreendimento não encontrado com id: " + idEmpreendimento));
        if (!empreendimentoReferencia.getStatus().equals(EmpreendimentoStatus.APROVADO)) {
            throw new EmpreendimentoNotApprovedException("Empreendimento não aprovado");
        }
        Empreendimento empreendimentoMapped = empreendimentoMapper.requestToEntity(data);
        Set<Ambiente> ambientesCopiados = new HashSet<>();
        for (Ambiente ambiente: empreendimentoReferencia.getAmbientes()) {
            ambientesCopiados.add(new Ambiente(ambiente, empreendimentoMapped));
        }
        empreendimentoMapped.setAmbientes(ambientesCopiados);
        Set<CombinacaoEMM> combinacoesCopiadas = new HashSet<>();
        for (CombinacaoEMM combinacao: empreendimentoReferencia.getMateriaisPorMarca()) {
            combinacoesCopiadas.add(new CombinacaoEMM(empreendimentoMapped, combinacao.getMaterial(), combinacao.getMarca()));
        }
        empreendimentoMapped.setMateriaisPorMarca(combinacoesCopiadas);
        Empreendimento empreendimentoSalvo = empreendimentoRepository.save(empreendimentoMapped);
        return empreendimentoMapper.toDto(empreendimentoSalvo);
    }
    @Transactional
    public void forceAprovacao(Integer idEmpreendimento) {
        Empreendimento empreendimento = empreendimentoRepository.findById(idEmpreendimento)
                .orElseThrow(() -> new ResourceNotFoundException("Empreendimento não encontrado com id: " + idEmpreendimento));
        empreendimento.setStatus(EmpreendimentoStatus.APROVADO);
        for (Ambiente ambiente: empreendimento.getAmbientes()) {
            ambiente.setStatus(AmbienteStatus.APROVADO);
            for (Item item: ambiente.getItens()) {
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

    @Transactional
    public EmpreendimentoResponse atualizarObservacao(Integer id, int index, EmpreendimentoObservacao data) {
        Empreendimento empreendimento = empreendimentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empreendimento não encontrado"));

        List<String> obs = empreendimento.getObservacoes();

        if (index < 0 || index >= obs.size()) {
            throw new EmpreendimentoBusinessLogicException("Índice de observação inválido");
        }

        obs.set(index, data.observacao());
        empreendimento.setObservacoes(obs);

        empreendimentoRepository.save(empreendimento);
        return empreendimentoMapper.toDto(empreendimento);
    }

    @Transactional
    public EmpreendimentoResponse removerObservacao(Integer id, int index) {
        Empreendimento empreendimento = empreendimentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empreendimento não encontrado"));

        List<String> obs = empreendimento.getObservacoes();

        if (index < 0 || index >= obs.size()) {
            throw new EmpreendimentoBusinessLogicException("Índice de observação inválido");
        }

        obs.remove(index);
        empreendimento.setObservacoes(obs);

        empreendimentoRepository.save(empreendimento);
        return empreendimentoMapper.toDto(empreendimento);
    }

    @Transactional
    public EmpreendimentoResponse limparObservacoes(Integer id) {
        Empreendimento empreendimento = empreendimentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empreendimento não encontrado"));

        empreendimento.setObservacoes(List.of()); // limpa tudo

        empreendimentoRepository.save(empreendimento);
        return empreendimentoMapper.toDto(empreendimento);
    }


}