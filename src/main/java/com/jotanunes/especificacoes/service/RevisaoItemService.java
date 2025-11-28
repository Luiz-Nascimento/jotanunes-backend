package com.jotanunes.especificacoes.service;

import com.jotanunes.especificacoes.dto.revisaoItens.RevisaoItemResponse;
import com.jotanunes.especificacoes.exception.ResourceNotFoundException;
import com.jotanunes.especificacoes.mapper.RevisaoItemMapper;
import com.jotanunes.especificacoes.model.Empreendimento;
import com.jotanunes.especificacoes.model.RevisaoItem;
import com.jotanunes.especificacoes.repository.EmpreendimentoRepository;
import com.jotanunes.especificacoes.repository.RevisaoItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
public class RevisaoItemService {

    private final RevisaoItemRepository repository;

    private final RevisaoItemMapper revisaoItemMapper;

    private final EmpreendimentoRepository empreendimentoRepository;

    public RevisaoItemService(RevisaoItemRepository repository, RevisaoItemMapper revisaoItemMapper, EmpreendimentoRepository empreendimentoRepository) {
        this.repository = repository;
        this.revisaoItemMapper = revisaoItemMapper;
        this.empreendimentoRepository = empreendimentoRepository;
    }

    public List<RevisaoItemResponse> findAll() {
        return revisaoItemMapper.toDtoList(repository.findAll());
    }

    public List<RevisaoItemResponse> findRevisoesByEmpreendimentoId(Integer idEmpreendimento) {
        boolean empreendimentoExists = empreendimentoRepository.existsById(idEmpreendimento);
        if (!empreendimentoExists) {
            throw new ResourceNotFoundException("Empreendimento não encontrado com id: " + idEmpreendimento);
        }
        List<RevisaoItem> revisoes = repository.findByItemAmbienteEmpreendimentoId(idEmpreendimento);
        return revisaoItemMapper.toDtoList(revisoes);
    }


}
