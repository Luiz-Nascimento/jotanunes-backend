package com.jotanunes.especificacoes.service;

import com.jotanunes.especificacoes.dto.ambiente.CatalogoAmbienteRequest;
import com.jotanunes.especificacoes.dto.ambiente.CatalogoAmbienteResponse;
import com.jotanunes.especificacoes.mapper.CatalogoAmbienteMapper;
import com.jotanunes.especificacoes.repository.CatalogoAmbienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatalogoAmbienteService {

    private final CatalogoAmbienteRepository repository;
    private final CatalogoAmbienteMapper catalogoAmbienteMapper;

    public CatalogoAmbienteService(CatalogoAmbienteRepository repository, CatalogoAmbienteMapper catalogoAmbienteMapper) {
        this.repository = repository;
        this.catalogoAmbienteMapper = catalogoAmbienteMapper;
    }

    public List<CatalogoAmbienteResponse> getAllCatalogoAmbientes() {
        return repository.findAll().stream().map(catalogoAmbienteMapper::toResponse).toList();
    }

    public CatalogoAmbienteResponse createCatalogoAmbiente(CatalogoAmbienteRequest request) {
        return catalogoAmbienteMapper.toResponse(repository.save(catalogoAmbienteMapper.toEntity(request)));
    }
}
