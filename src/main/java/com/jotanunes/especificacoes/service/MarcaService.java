package com.jotanunes.especificacoes.service;

import com.jotanunes.especificacoes.dto.marca.MarcaRequest;
import com.jotanunes.especificacoes.dto.marca.MarcaResponse;
import com.jotanunes.especificacoes.exception.ResourceNotFoundException;
import com.jotanunes.especificacoes.mapper.MarcaMapper;
import com.jotanunes.especificacoes.model.Marca;
import com.jotanunes.especificacoes.repository.MarcaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MarcaService {

    private final MarcaRepository repository;
    private final MarcaMapper mapper;
    private final Logger logger = LoggerFactory.getLogger(MarcaService.class);

    public MarcaService(MarcaRepository repository, MarcaMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public MarcaResponse create(MarcaRequest dto) {
        Marca marca = mapper.toEntity(dto);
        MarcaResponse response = mapper.toDTO(repository.save(marca));
        logger.info("Marca criada com id: {}", response.id());
        return response;
    }

    public List<MarcaResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public MarcaResponse findById(Integer id) {
        Marca marca = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Marca não encontrada com id: "+ id));
        return mapper.toDTO(marca);
    }

    @Transactional
    public MarcaResponse update(Integer id, MarcaRequest dto) {
        Marca marca = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Marca não encontrada com id:" + id));
        marca.setNome(dto.nome());
        return mapper.toDTO(repository.save(marca));
    }

    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Marca não encontrada com id: " + id);
        }
        repository.deleteById(id);
        logger.info("Marca deletada com id: {}", id);
    }
}