package com.jotanunes.especificacoes.service;

import com.jotanunes.especificacoes.dto.marca.MarcaRequest;
import com.jotanunes.especificacoes.dto.marca.MarcaResponse;
import com.jotanunes.especificacoes.mapper.MarcaMapper;
import com.jotanunes.especificacoes.model.Marca;
import com.jotanunes.especificacoes.repository.MarcaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MarcaService {

    @Autowired
    private MarcaRepository repository;
    @Autowired
    private MarcaMapper mapper;

    public MarcaResponse create(MarcaRequest dto) {
        Marca marca = mapper.toEntity(dto);
        return mapper.toDTO(repository.save(marca));
    }

    public List<MarcaResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public MarcaResponse findById(Integer id) {
        Marca marca = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Marca não encontrada com id: "+ id));
        return mapper.toDTO(marca);
    }
    @Transactional
    public MarcaResponse atualizar(Integer id, MarcaRequest dto) {
        Marca marca = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Marca não encontrada"));
        marca.setNome(dto.nome());
        return mapper.toDTO(repository.save(marca));
    }

    public void deletar(Integer id) {
        repository.deleteById(id);
    }
}