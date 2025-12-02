package com.jotanunes.especificacoes.service;

import com.jotanunes.especificacoes.dto.ambiente.CatalogoAmbienteRequest;
import com.jotanunes.especificacoes.dto.ambiente.CatalogoAmbienteResponse;
import com.jotanunes.especificacoes.exception.ResourceNotFoundException;
import com.jotanunes.especificacoes.mapper.CatalogoAmbienteMapper;
import com.jotanunes.especificacoes.model.CatalogoAmbiente;
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

    public CatalogoAmbienteResponse update(Integer id, CatalogoAmbienteRequest request) {
        CatalogoAmbiente ambiente = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ambiente não encontrado com id: " + id));

        ambiente.setNome(request.nome());
        ambiente.setTipo(request.tipo());

        return catalogoAmbienteMapper.toResponse(repository.save(ambiente));
    }

    public void delete(Integer id) {
        CatalogoAmbiente ambiente = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ambiente não encontrado com id: " + id));

        repository.delete(ambiente);
    }
}
