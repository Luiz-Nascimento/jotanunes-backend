package com.jotanunes.especificacoes.service;

import com.jotanunes.especificacoes.dto.empreendimento.EmpreendimentoRequest;
import com.jotanunes.especificacoes.dto.empreendimento.EmpreendimentoResponse;
import com.jotanunes.especificacoes.mapper.EmpreendimentoMapper;
import com.jotanunes.especificacoes.model.Empreendimento;
import com.jotanunes.especificacoes.repository.EmpreendimentoRepository;
import jakarta.persistence.EntityNotFoundException;
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

    public EmpreendimentoResponse findById(Integer id) {
        Empreendimento empreendimento = empreendimentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Empreedimento não encontrado"));
        return empreendimentoMapper.toDto(empreendimento);
    }

    public EmpreendimentoResponse create(EmpreendimentoRequest data) {
        Empreendimento empreendimento = empreendimentoMapper.requestToEntity(data);
        System.out.println(empreendimento.getObservacoes());
        empreendimentoRepository.save(empreendimento);
        return empreendimentoMapper.toDto(empreendimento);
    }

    public void delete(Integer id) {
        if (empreendimentoRepository.existsById(id)) {
            empreendimentoRepository.deleteById(id);
        }
        else {
            throw new EntityNotFoundException("Empreendimento não encontrado");
        }
    }
}
