package com.jotanunes.especificacoes.service;

import com.jotanunes.especificacoes.dto.material.MaterialRequest;
import com.jotanunes.especificacoes.dto.material.MaterialResponse;
import com.jotanunes.especificacoes.mapper.MaterialMapper;
import com.jotanunes.especificacoes.model.Material;
import com.jotanunes.especificacoes.repository.MaterialRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MaterialService {

    @Autowired
    private MaterialRepository repository;
    @Autowired
    private MaterialMapper mapper;

    public MaterialResponse create(MaterialRequest dto) {
        Material material = mapper.toEntity(dto);
        return mapper.toDTO(repository.save(material));
    }

    public List<MaterialResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public MaterialResponse findById(Integer id) {
        Material material = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Material não encontrado com id: " + id));
        return mapper.toDTO(material);
    }

    @Transactional
    public MaterialResponse update(Integer id, MaterialRequest dto) {
        Material material = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Material não encontrado"));
        material.setNome(dto.nome());
        return mapper.toDTO(repository.save(material));
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }
}