package com.jotanunes.especificacoes.service;

import com.jotanunes.especificacoes.dto.empreendimento.EmpreendimentoResponse;
import com.jotanunes.especificacoes.mapper.EmpreendimentoMapper;
import com.jotanunes.especificacoes.repository.EmpreendimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpreendimentoService {

    @Autowired
    private EmpreendimentoRepository empreendimentoRepository;

    @Autowired
    private EmpreendimentoMapper empreendimentoMapper;

    public List<EmpreendimentoResponse> findAll() {
        return empreendimentoRepository.findAll().stream()
                .map(empreendimento -> empreendimentoMapper.toDto(empreendimento))
                .toList();
    }
}
